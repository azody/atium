package azody.atium

import azody.atium.backtest.Backtest
import azody.atium.domain.CashPosition
import azody.atium.domain.Portfolio
import azody.atium.domain.Strategy
import azody.atium.indicator.ohlc.trendfollowing.BottlePatternIndicator
import azody.atium.marketdata.AlpacaMarketData
import azody.atium.performance.Performance
import java.math.BigDecimal

fun main(args: Array<String>) {
    val ohlc = AlpacaMarketData.getOHLCData("UPRO", "2024-01-01", "2025-01-01", "1D")

    val strategy =
        Strategy(
            startingPortfolio =
                Portfolio(
                    positions = listOf(),
                    cashPosition = CashPosition("USD", BigDecimal(500)),
                ),
            indicator = BottlePatternIndicator,
            instrument = "UPRO",
        )

    val results = Backtest.runSingleAssetBackTest(strategy, ohlc)
    Performance.printSummary(results)
}
