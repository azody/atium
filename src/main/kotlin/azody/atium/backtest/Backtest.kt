package azody.atium.backtest

import azody.atium.accounting.Accounting
import azody.atium.domain.*
import java.math.BigDecimal

object Backtest {
    /**
     * Runs a simple test that buys on a bull indicator and sells on a bear indicator
     */
    fun runSingleAssetBackTest(
        strategy: Strategy,
        data: List<OHLC>,
    ): BackTestResults {
        val tradeSeries = mutableMapOf<Long, List<Trade>>()
        val portfolioSeries = mutableMapOf<Long, Portfolio>()

        var previousPortfolio = strategy.startingPortfolio

        data.forEachIndexed { index, _ ->
            if (index >= data.size) {
                return@forEachIndexed
            }

            val currentPosition = getCurrentPosition(previousPortfolio, strategy.instrument)
            val currentSize = currentPosition?.quantity ?: BigDecimal.ZERO

            // Handle buy signals
            if (shouldBuy(strategy, data, index, currentSize)) {
                val buyTrade = createBuyTrade(strategy, data, index, currentSize)
                if (canExecuteTrade(strategy, buyTrade, previousPortfolio)) {
                    tradeSeries[buyTrade.businessTime] = listOf(buyTrade)
                }
            }

            // Handle sell signals
            if (currentPosition != null) {
                val sellTrade = checkSellConditions(strategy, currentPosition, data, index)
                if (sellTrade != null) {
                    tradeSeries[sellTrade.businessTime] = listOf(sellTrade)
                }
            }

            // Update portfolio
            previousPortfolio = updatePortfolio(previousPortfolio, data[index], tradeSeries)
            portfolioSeries[data[index].businessTime] = previousPortfolio
        }

        return BackTestResults(
            portfolioSeries = portfolioSeries,
            tradeSeries = tradeSeries,
        )
    }

    private fun getCurrentPosition(portfolio: Portfolio, instrument: String): Position? =
        portfolio.positions.firstOrNull { 
            it.instrument == instrument && it.quantity > BigDecimal.ZERO 
        }

    private fun shouldBuy(strategy: Strategy, data: List<OHLC>, index: Int, currentSize: BigDecimal): Boolean =
        strategy.indicator.bullIndicator(data, index) && currentSize < strategy.maxPositionSize

    private fun createBuyTrade(strategy: Strategy, data: List<OHLC>, index: Int, currentSize: BigDecimal): Trade {
        val businessTime = data[index + 1].businessTime
        val remainingSize = strategy.maxPositionSize - currentSize
        val buyQuantity = BigDecimal.ONE.min(remainingSize)
        
        return Trade(
            businessTime = businessTime,
            instrument = strategy.instrument,
            counterInstrument = "USD",
            settlementInstrument = "USD",
            price = data[index + 1].open.toBigDecimal(),
            quantity = buyQuantity,
            type = TradeType.BUY,
            tradeSubType = TradeSubType.NONE,
            direction = Direction.LONG,
        )
    }

    private fun canExecuteTrade(strategy: Strategy, trade: Trade, portfolio: Portfolio): Boolean =
        if (!strategy.allowMargin) {
            val tradeCost = trade.price * trade.quantity
            tradeCost < portfolio.cashPosition.quantity
        } else {
            true // TODO: Implement margin logic
        }

    private fun checkSellConditions(
        strategy: Strategy,
        currentPosition: Position,
        data: List<OHLC>,
        index: Int
    ): Trade? {
        var shouldSell = false
        var sellPrice = BigDecimal.ZERO
        var sellTime = 0L

        // Check profit/loss exit conditions
        if (strategy.exitStrategy == ExitStrategy.PROFIT_LOSS_TARGETS || strategy.exitStrategy == ExitStrategy.BOTH) {
            val averageEntryPrice = calculateAverageEntryPrice(currentPosition)
            val currentPrice = data[index].close.toBigDecimal()
            val priceChange = calculatePriceChange(currentPrice, averageEntryPrice)
            
            if (shouldExitOnPriceChange(priceChange, strategy)) {
                shouldSell = true
                sellPrice = currentPrice
                sellTime = data[index].businessTime
            }
        }

        // Check indicator exit condition
        if (!shouldSell && 
            (strategy.exitStrategy == ExitStrategy.INDICATOR_SIGNAL || strategy.exitStrategy == ExitStrategy.BOTH) &&
            strategy.indicator.bearIndicator(data, index)) {
            shouldSell = true
            sellPrice = data[index + 1].open.toBigDecimal()
            sellTime = data[index + 1].businessTime
        }

        return if (shouldSell) {
            createSellTrade(currentPosition, sellPrice, sellTime)
        } else {
            null
        }
    }

    private fun calculateAverageEntryPrice(position: Position): BigDecimal {
        val totalQuantity = position.lots.sumOf { it.quantity }
        val weightedSum = position.lots.sumOf { lot -> lot.price * lot.quantity }
        return weightedSum.divide(totalQuantity, 4, java.math.RoundingMode.HALF_UP)
    }

    private fun calculatePriceChange(currentPrice: BigDecimal, entryPrice: BigDecimal): Double =
        (currentPrice - entryPrice)
            .divide(entryPrice, 4, java.math.RoundingMode.HALF_UP)
            .toDouble()

    private fun shouldExitOnPriceChange(priceChange: Double, strategy: Strategy): Boolean =
        (strategy.profitTarget != null && priceChange >= strategy.profitTarget) ||
        (strategy.stopLoss != null && priceChange <= -strategy.stopLoss)

    private fun createSellTrade(position: Position, price: BigDecimal, time: Long): Trade =
        Trade(
            businessTime = time,
            instrument = position.instrument,
            counterInstrument = "USD",
            settlementInstrument = "USD",
            price = price,
            quantity = position.quantity,
            type = TradeType.SELL,
            tradeSubType = TradeSubType.NONE,
            direction = Direction.LONG,
        )

    private fun updatePortfolio(
        portfolio: Portfolio,
        currentData: OHLC,
        tradeSeries: Map<Long, List<Trade>>
    ): Portfolio {
        var newPortfolio = updatePositionPrices(portfolio, currentData)
        val previousDayTrades = tradeSeries[currentData.businessTime] ?: emptyList()
        previousDayTrades.forEach {
            newPortfolio = Accounting.addTrade(it, newPortfolio)
        }
        return newPortfolio
    }

    private fun updatePositionPrices(portfolio: Portfolio, currentData: OHLC): Portfolio =
        Portfolio(
            cashPosition = portfolio.cashPosition,
            positions = portfolio.positions.map {
                Position(
                    instrument = it.instrument,
                    counterInstrument = it.counterInstrument,
                    price = currentData.close.toBigDecimal(),
                    quantity = it.quantity,
                    direction = it.direction,
                    lots = it.lots,
                )
            },
            sellMethodology = portfolio.sellMethodology,
        )
}
