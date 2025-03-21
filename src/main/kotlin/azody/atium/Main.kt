package azody.atium

import azody.atium.backtest.Backtest
import azody.atium.domain.CashPosition
import azody.atium.domain.Portfolio
import azody.atium.domain.Strategy
import azody.atium.domain.ExitStrategy
import azody.atium.indicator.ohlc.trendfollowing.BottlePatternIndicator
import azody.atium.indicator.ohlc.contrarian.EngulfingPatternIndicator
import azody.atium.indicator.ohlc.contrarian.EuphoriaPatternIndicator
import azody.atium.marketdata.AlpacaMarketData
import azody.atium.performance.Performance
import azody.atium.visualization.ChartVisualizer
import java.math.BigDecimal

fun main(args: Array<String>) {
    val ohlc = AlpacaMarketData.getOHLCData("SPY", "2024-06-01", "2025-03-18", "1Hour")

    // Example 1: Using only profit/loss targets
    val strategyProfitLoss =
        Strategy(
            startingPortfolio =
                Portfolio(
                    positions = listOf(),
                    cashPosition = CashPosition("USD", BigDecimal(250)),
                ),
            indicator = BottlePatternIndicator,
            instrument = "GLD",
            profitTarget = 0.25,  // Exit at 5% profit
            stopLoss = 0.10,      // Exit at 2% loss
            exitStrategy = ExitStrategy.PROFIT_LOSS_TARGETS,
            maxPositionSize = BigDecimal(3)  // Maximum of 3 units
        )

    // Example 2: Using only indicator signals (original behavior)
    val strategyIndicator =
        Strategy(
            startingPortfolio =
                Portfolio(
                    positions = listOf(),
                    cashPosition = CashPosition("USD", BigDecimal(10_000)),
                ),
            indicator = EuphoriaPatternIndicator,
            instrument = "GLD",
            exitStrategy = ExitStrategy.INDICATOR_SIGNAL,
            maxPositionSize = BigDecimal(5)  // Maximum of 5 units (default)
        )

    // Example 3: Using both profit/loss targets and indicator signals
    val strategyBoth =
        Strategy(
            startingPortfolio =
                Portfolio(
                    positions = listOf(),
                    cashPosition = CashPosition("USD", BigDecimal(250)),
                ),
            indicator = BottlePatternIndicator,
            instrument = "GLD",
            profitTarget = 0.05,
            stopLoss = 0.03,
            exitStrategy = ExitStrategy.BOTH,
            maxPositionSize = BigDecimal(2)  // Maximum of 2 units
        )

    // Choose which strategy to test
    val strategy = strategyIndicator  // Change this to test different strategies

    val results = Backtest.runSingleAssetBackTest(strategy, ohlc)
    
    // Print numerical summary
    val priceData = ohlc.map { it.close }
    Performance.printSummary(results, priceData)

    // Visualize results
    val timestamps = ohlc.map { it.businessTime }
    ChartVisualizer.visualizeResults(results, priceData, timestamps)
}
