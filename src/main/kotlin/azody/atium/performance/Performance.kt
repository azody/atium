package azody.atium.performance

import azody.atium.backtest.BackTestResults
import azody.atium.domain.Portfolio
import azody.atium.domain.Trade
import azody.atium.domain.TradeType
import azody.atium.domain.getTotalValue
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

data class StrategyMetrics(
    val strategyName: String,
    val totalTrades: Int,
    val buyTrades: Int,
    val sellTrades: Int,
    val hitRatio: BigDecimal,
    val strategyReturn: Double,
    val outperformance: Double,
    val maxDrawdown: BigDecimal
)

object Performance {
    fun calculateMetrics(backTestResults: BackTestResults, priceData: List<Double>, strategyName: String): StrategyMetrics {
        val trades = backTestResults.tradeSeries.values.flatten()
        
        val strategyReturn = calculateStrategyReturn(trades, priceData)
        val assetReturn = getAssetReturn(priceData)
        val outperformance = strategyReturn - assetReturn
        val maxDrawdown = getMaxDrawdown(backTestResults)

        return StrategyMetrics(
            strategyName = strategyName,
            totalTrades = trades.size,
            buyTrades = trades.count { it.type == TradeType.BUY },
            sellTrades = trades.count { it.type == TradeType.SELL },
            hitRatio = getHitRatio(trades),
            strategyReturn = strategyReturn,
            outperformance = outperformance,
            maxDrawdown = maxDrawdown
        )
    }

    fun printStrategyComparison(metrics: List<StrategyMetrics>, assetReturn: Double) {
        println("\nStrategy Comparison:")
        println("Asset Buy & Hold Return: ${(assetReturn * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}%")
        println("\nRanked by Outperformance:")
        
        metrics.sortedByDescending { it.outperformance }.forEach { metric ->
            println("\n${metric.strategyName}:")
            println("Total Return: ${(metric.strategyReturn * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}%")
            println("Outperformance: ${(metric.outperformance * 100).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}%")
            println("Hit Ratio: ${(metric.hitRatio * BigDecimal(100)).setScale(2, RoundingMode.HALF_UP)}%")
            println("Max Drawdown: ${(metric.maxDrawdown * BigDecimal(100)).setScale(2, RoundingMode.HALF_UP)}%")
            println("Trade Count: ${metric.totalTrades} (${metric.buyTrades} buys, ${metric.sellTrades} sells)")
        }
    }

    private fun calculateStrategyReturn(trades: List<Trade>, priceData: List<Double>): Double {
        if (trades.isEmpty() || priceData.isEmpty()) return 0.0
        
        var totalReturn = 1.0  // Start with 1.0 (100%) and multiply by (1 + return) for each period
        var inPosition = false
        var entryPrice = 0.0

        for (trade in trades) {
            when (trade.type) {
                TradeType.BUY -> {
                    if (!inPosition) {
                        inPosition = true
                        entryPrice = trade.price.toDouble()
                    }
                }
                TradeType.SELL -> {
                    if (inPosition) {
                        val periodReturn = (trade.price.toDouble() / entryPrice) - 1.0
                        totalReturn *= (1.0 + periodReturn)
                        inPosition = false
                    }
                }
                TradeType.INCOME -> {}
            }
        }

        // Handle open position using last price
        if (inPosition && priceData.isNotEmpty()) {
            val lastPrice = priceData.last()
            val periodReturn = (lastPrice / entryPrice) - 1.0
            totalReturn *= (1.0 + periodReturn)
        }

        return totalReturn - 1.0  // Convert back to percentage return
    }

    private fun getAssetReturn(priceData: List<Double>): Double {
        if (priceData.size < 2) return 0.0
        val firstPrice = priceData.first()
        val lastPrice = priceData.last()
        return (lastPrice / firstPrice) - 1.0
    }

    fun getHitRatio(trades: List<Trade>): BigDecimal {
        val buyQueue = ArrayDeque<Trade>()
        var profitableTrades = 0
        var matchedTrades = 0

        for (trade in trades) {
            when (trade.type) {
                TradeType.BUY -> buyQueue.add(trade)
                TradeType.SELL -> {
                    if (buyQueue.isNotEmpty()) {
                        val buyTrade = buyQueue.removeFirst()
                        if (trade.price > buyTrade.price) {
                            profitableTrades++
                        }
                        matchedTrades++
                    }
                }
                TradeType.INCOME -> {}
            }
        }

        return if (matchedTrades == 0) {
            BigDecimal.ZERO
        } else {
            BigDecimal(profitableTrades)
                .setScale(16)
                .divide(BigDecimal(matchedTrades), RoundingMode.HALF_UP)
                .stripTrailingZeros()
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
