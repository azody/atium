package azody.atium.backtest

import azody.atium.accounting.Accounting
import azody.atium.domain.*
import java.math.BigDecimal

object Backtest {
    /**
     * Runs a simple test that buys on a bull indicator and sells on a bear indicator
     * TODO: Missing: Exit Strategy
     * TODO: Missing Quantity Logic
     */
    fun runSingleAssetBackTest(
        strategy: Strategy,
        data: List<OHLC>,
    ): BackTestResults {
        val tradeSeries = mutableMapOf<Long, List<Trade>>()
        val portfolioSeries = mutableMapOf<Long, Portfolio>()

        var previousPortfolio = strategy.startingPortfolio

        data.forEachIndexed { index, _ ->
            val newTrades = mutableListOf<Trade>() // Does Order Matter? Thinking Sells than buys but revisit

            if (strategy.indicator.bullIndicator(data, index)) {
                val businessTime = data[index + 1].businessTime

                val trade =
                    Trade(
                        businessTime = businessTime,
                        instrument = strategy.instrument,
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = data[index + 1].open.toBigDecimal(),
                        quantity = BigDecimal(10),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    )
                tradeSeries[businessTime] = listOf(trade)
            } else if (strategy.indicator.bearIndicator(data, index)) {
                val outstandingQuantity = previousPortfolio.positions.filter { it.instrument == strategy.instrument }.sumOf { it.quantity }
                if (outstandingQuantity >= BigDecimal(10) || strategy.allowShort) {
                    val businessTime = data[index + 1].businessTime

                    val trade =
                        Trade(
                            businessTime = businessTime,
                            instrument = strategy.instrument,
                            counterInstrument = "USD",
                            settlementInstrument = "USD",
                            price = data[index + 1].open.toBigDecimal(),
                            quantity = BigDecimal(10),
                            type = TradeType.SELL,
                            tradeSubType = TradeSubType.NONE,
                            direction = Direction.LONG,
                        )
                    tradeSeries[businessTime] = listOf(trade)
                    previousPortfolio = Accounting.addTrade(trade, previousPortfolio)
                }
            }

            // Update Portfolio pricing and apply trades
            var newPortfolio =
                Portfolio(
                    cashPosition = previousPortfolio.cashPosition,
                    positions =
                        previousPortfolio.positions.filter { it.instrument == strategy.instrument }.map {
                            Position(
                                instrument = it.instrument,
                                counterInstrument = it.counterInstrument,
                                price = data[index].close.toBigDecimal(),
                                quantity = it.quantity,
                                direction = it.direction,
                                lots = it.lots,
                            )
                        },
                    sellMethodology = previousPortfolio.sellMethodology,
                )
            val previousDayTrades = tradeSeries[data[index].businessTime] ?: emptyList()

            previousDayTrades.forEach {
                newPortfolio = Accounting.addTrade(it, newPortfolio)
            }
            portfolioSeries[data[index].businessTime] = newPortfolio
            previousPortfolio = newPortfolio
        }

        return BackTestResults(
            portfolioSeries = portfolioSeries,
            tradeSeries = tradeSeries,
        )
    }
}
