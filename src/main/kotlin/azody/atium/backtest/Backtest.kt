package azody.atium.backtest

import azody.atium.accounting.Accounting
import azody.atium.domain.*
import java.math.BigDecimal

object Backtest {
    /**
     * Runs a simple test that buys on a bull indicator and sells on a bear indicator
     * Assumes a Starting balance of 1_000
     */
    fun runSingleAssetBackTest(
        startingPortfolio: Portfolio,
        data: List<OHLC>,
        indicator: Indicator,
        instrument: String,
    ): Portfolio {
        var portfolio = startingPortfolio

        data.forEachIndexed { index, _ ->

            if (indicator.bullIndicator(data, index)) {
                val trade =
                    Trade(
                        businessTime = data[index + 1].businessTime,
                        instrument = instrument,
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = data[index + 1].open.toBigDecimal(),
                        quantity = BigDecimal(10),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    )
                portfolio = Accounting.addTrade(trade, portfolio)
            } else if (indicator.bearIndicator(data, index)) {
                val trade =
                    Trade(
                        businessTime = data[index + 1].businessTime,
                        instrument = instrument,
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = data[index + 1].open.toBigDecimal(),
                        quantity = BigDecimal(10),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    )
                portfolio = Accounting.addTrade(trade, portfolio)
            }
        }
        return portfolio
    }
}
