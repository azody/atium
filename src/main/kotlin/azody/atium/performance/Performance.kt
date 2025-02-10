package azody.atium.performance

import azody.atium.backtest.BackTestResults
import azody.atium.domain.Trade
import azody.atium.domain.TradeType
import java.math.BigDecimal
import java.math.RoundingMode

object Performance {
    // TODO: Only works with single asset at the moment
    fun printSummary(backTestResults: BackTestResults) {
        val trades = backTestResults.tradeSeries.values.flatten()
        println("Number of Trades: ${trades.size}")
        println("\tBuy Trades: ${trades.filter { it.type == TradeType.BUY }}")
        println("\tSell Trades: ${trades.filter { it.type == TradeType.SELL }}")
        println()
        println("Open Positions:")
        val lastPortfolio = backTestResults.portfolioSeries.maxBy { it.key }.value
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

        println("Hit Ratio: ${getHitRatio(trades)}")
    }

    /**
     * Calculates the hit ratio
     * Hit Ratio = (Number of Profitable Trades) / (Number of Total Trades)
     * Edge Cases
     *  - Unmatched BUY trades left in the queue do not count towards the hit ratio
     *  - No trades or matched BUYs results in a hit ratio of 0.0
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

                    while (remainingSellAmount > BigDecimal.ZERO && buyQueue.isNotEmpty()) {
                        val buyTrade = buyQueue.removeFirst()

                        if (remainingSellAmount > buyTrade.quantity) {
                            // Fully match the BUY trade
                            val buyCost = buyTrade.quantity * buyTrade.price
                            val sellRevenue = trade.quantity * trade.price
                            if (sellRevenue > buyCost) {
                                profitableTrades++
                            }
                            matchedTrades++
                            remainingSellAmount -= buyTrade.quantity
                        } else {
                            // Partially math the Buy Trade
                            val matchedAmount = remainingSellAmount
                            val buyCost = matchedAmount * buyTrade.price
                            val sellRevenue = matchedAmount * trade.price
                            if (sellRevenue > buyCost) {
                                profitableTrades++
                            }
                            matchedTrades++

                            // Update the remaining amount of BUY trade and put it back in the deque
                            val remainingBuyAmount = buyTrade.quantity - matchedAmount
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
                            remainingSellAmount = BigDecimal.ZERO
                        }
                    }
                    if (buyQueue.isNotEmpty()) {
                        val buyTrade = buyQueue.removeFirst()
                        val buyCost = buyTrade.quantity * buyTrade.price
                        val sellRevenue = trade.quantity * trade.price

                        // Calculate profit/loss
                        if (sellRevenue > buyCost) {
                            profitableTrades++
                        }
                        matchedTrades++
                    }
                }

                TradeType.INCOME -> {}
            }
        }

        return if (matchedTrades ==
            0
        ) {
            BigDecimal.ZERO
        } else {
            (BigDecimal(profitableTrades) / BigDecimal(matchedTrades).setScale(16, RoundingMode.HALF_UP))
        }
    }
}
