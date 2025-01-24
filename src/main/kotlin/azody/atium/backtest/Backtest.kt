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
        var resultingPortfolio = strategy.startingPortfolio
        val trades = mutableListOf<Trade>()
        data.forEachIndexed { index, _ ->

            if (strategy.indicator.bullIndicator(data, index)) {
                val trade =
                    Trade(
                        businessTime = data[index + 1].businessTime,
                        instrument = strategy.instrument,
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = data[index + 1].open.toBigDecimal(),
                        quantity = BigDecimal(10),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    )
                trades.add(trade)
                resultingPortfolio = Accounting.addTrade(trade, resultingPortfolio)
            } else if (strategy.indicator.bearIndicator(data, index)) {
                val outstandingQuantity = resultingPortfolio.positions.filter { it.instrument == strategy.instrument }.sumOf { it.quantity }
                if (outstandingQuantity >= BigDecimal(10) || strategy.allowShort) {
                    val trade =
                        Trade(
                            businessTime = data[index + 1].businessTime,
                            instrument = strategy.instrument,
                            counterInstrument = "USD",
                            settlementInstrument = "USD",
                            price = data[index + 1].open.toBigDecimal(),
                            quantity = BigDecimal(10),
                            type = TradeType.SELL,
                            tradeSubType = TradeSubType.NONE,
                            direction = Direction.LONG,
                        )
                    trades.add(trade)
                    resultingPortfolio = Accounting.addTrade(trade, resultingPortfolio)
                }
            }
        }
        return BackTestResults(
            startingPortfolio = strategy.startingPortfolio,
            resultingPortfolio = resultingPortfolio,
            trades = trades,
            finalValues = mapOf(strategy.instrument to data.last().close.toBigDecimal()),
        )
    }
}
