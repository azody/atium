package azody.atium.performance

import azody.atium.backtest.BackTestResults
import azody.atium.domain.Portfolio
import azody.atium.domain.Trade
import azody.atium.domain.TradeType
import azody.atium.domain.getTotalValue
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

object Performance {
    fun printSummary(backTestResults: BackTestResults, priceData: List<Double>) {
        val trades = backTestResults.tradeSeries.values.flatten()
        val firstPortfolio = backTestResults.portfolioSeries.minBy { it.key }.value
        val lastPortfolio = backTestResults.portfolioSeries.maxBy { it.key }.value

        // Calculate returns based on position value changes only
        val strategyReturn = calculateStrategyReturn(backTestResults, priceData)
        val assetReturn = getAssetReturn(priceData)
        val outperformance = strategyReturn - assetReturn

        // Strategy Statistics
        println("\nStrategy Statistics:")
        println("Number of Trades: ${trades.size}")
        println("\tBuy Trades: ${trades.filter { it.type == TradeType.BUY }.size}")
        println("\tSell Trades: ${trades.filter { it.type == TradeType.SELL }.size}")
        println("Hit Ratio: ${getHitRatio(trades)}")
        
        // Returns Comparison
        println("\nReturns Comparison (excluding cash drag):")
        println("Strategy Return: ${(strategyReturn * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}%")
        println("Asset Return: ${(assetReturn * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}%")
        println("Outperformance: ${(outperformance * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}%")

        // Current Portfolio State
        println("\nCurrent Portfolio State:")
        println("\tCash: ${lastPortfolio.cashPosition.quantity}")
        lastPortfolio.positions.forEach {
            println(
                "\tInstrument: ${it.instrument} Quantity: ${it.quantity} Price: ${
                    lastPortfolio.positions
                        .firstOrNull { p ->
                            p.instrument == it.instrument
                        }?.price ?: -1
                }",
            )
        }
    }

    private fun calculateStrategyReturn(backTestResults: BackTestResults, priceData: List<Double>): Double {
        val trades = backTestResults.tradeSeries.values.flatten()
        var totalReturn = 0.0
        var currentPosition = 0.0
        var lastBuyPrice = 0.0

        // Process each trade to track position value changes
        for (trade in trades) {
            when (trade.type) {
                TradeType.BUY -> {
                    currentPosition += trade.quantity.toDouble()
                    lastBuyPrice = trade.price.toDouble()
                }
                TradeType.SELL -> {
                    if (currentPosition > 0 && lastBuyPrice > 0) {
                        val tradeReturn = (trade.price.toDouble() - lastBuyPrice) / lastBuyPrice
                        totalReturn += tradeReturn * (trade.quantity.toDouble() / currentPosition)
                        currentPosition -= trade.quantity.toDouble()
                    }
                }
                TradeType.INCOME -> {}
            }
        }

        // Account for any open position using the last price
        if (currentPosition > 0 && lastBuyPrice > 0 && priceData.isNotEmpty()) {
            val lastPrice = priceData.last()
            val openPositionReturn = (lastPrice - lastBuyPrice) / lastBuyPrice
            totalReturn += openPositionReturn
        }

        return totalReturn
    }

    private fun getAssetReturn(priceData: List<Double>): Double {
        if (priceData.size < 2) return 0.0
        val firstPrice = priceData.first()
        val lastPrice = priceData.last()
        return (lastPrice - firstPrice) / firstPrice
    }

    /**
     * Calculates the hit ratio
     * Hit Ratio = (Number of Profitable Trades) / (Number of Total Trades)
     * Edge Cases
     *  - Unmatched BUY trades left in the queue do not count towards the hit ratio - Done
     *  - No trades or matched BUYs results in a hit ratio of 0.0 - Done
     */
    fun getHitRatio(trades: List<Trade>): BigDecimal {
        val buyQueue = ArrayDeque<Trade>()
        var profitableTrades = 0
        var matchedTrades = 0

        for (trade in trades) {
            when (trade.type) {
                TradeType.BUY -> buyQueue.add(trade)
                TradeType.SELL -> {
                    var remainingSellAmount = trade.quantity
                    var totalBuyCost = BigDecimal.ZERO
                    var totalSellRevenue = trade.quantity * trade.price

                    while (remainingSellAmount > BigDecimal.ZERO && buyQueue.isNotEmpty()) {
                        val buyTrade = buyQueue.removeFirst()

                        if (remainingSellAmount > buyTrade.quantity) {
                            // Fully match the BUY trade
                            totalBuyCost += buyTrade.quantity * buyTrade.price
                            val sellRevenue = trade.quantity * trade.price
                            matchedTrades++
                            remainingSellAmount -= buyTrade.quantity
                        } else {
                            // Partially math the Buy Trade
                            val matchedAmount = remainingSellAmount
                            totalBuyCost += matchedAmount * buyTrade.price

                            // Update the remaining amount of BUY trade and put it back in the deque
                            val remainingBuyAmount = buyTrade.quantity - matchedAmount
                            if (remainingBuyAmount > BigDecimal.ZERO) {
                                buyQueue.addFirst(
                                    Trade(
                                        buyTrade.businessTime,
                                        buyTrade.instrument,
                                        buyTrade.counterInstrument,
                                        buyTrade.settlementInstrument,
                                        buyTrade.price,
                                        remainingBuyAmount,
                                        buyTrade.type,
                                        buyTrade.tradeSubType,
                                        buyTrade.direction,
                                    ),
                                )
                            }
                            remainingSellAmount = BigDecimal.ZERO
                        }
                    }

                    // Calculate profit/loss
                    if (totalSellRevenue > totalBuyCost) {
                        profitableTrades++
                    }
                    matchedTrades++
                }

                TradeType.INCOME -> {}
            }
        }

        return if (matchedTrades == 0) {
            BigDecimal.ZERO
        } else {
            (
                BigDecimal(profitableTrades).setScale(16) /
                    BigDecimal(matchedTrades).setScale(
                        16,
                        RoundingMode.HALF_UP,
                    )
            ).stripTrailingZeros()
        }
    }

    fun getPercentChange(
        startingPortfolio: Portfolio,
        endPortfolio: Portfolio,
    ): BigDecimal {
        val totalStartingValue =
            startingPortfolio.positions
                .sumOf {
                    it.price * it.quantity
                }.add(startingPortfolio.cashPosition.quantity)
        println("Starting Value: $totalStartingValue")
        val totalEndValue =
            endPortfolio.positions
                .sumOf {
                    it.price * it.quantity
                }.add(endPortfolio.cashPosition.quantity)
        println("End Value: $totalEndValue")

        return (totalEndValue - totalStartingValue)
            .setScale(16)
            .divide(totalStartingValue, RoundingMode.HALF_UP)
            .stripTrailingZeros()
    }

    fun getMaxDrawdown(backTestResults: BackTestResults): BigDecimal {
        val portfolioValues = backTestResults.portfolioSeries.values.map { it.getTotalValue() }
        var maxDrawdown = BigDecimal.ZERO
        var currentMaximum = BigDecimal.ZERO

        portfolioValues.forEachIndexed { index, value ->
            if (index == 0) {
                currentMaximum = value
            } else {
                // Update the maximum value if current value is higher
                if (value > currentMaximum) {
                    currentMaximum = value
                }
                // Calculate drawdown if current value is lower than maximum
                else if (currentMaximum > BigDecimal.ZERO) {
                    // Calculate current drawdown as percentage
                    val drawdown = (currentMaximum - value).divide(currentMaximum, 16, RoundingMode.HALF_UP)
                    // Update max drawdown if current drawdown is larger
                    if (drawdown > maxDrawdown) {
                        maxDrawdown = drawdown
                    }
                }
            }
        }

        return maxDrawdown
    }
}
