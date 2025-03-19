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
    // Configure test parameters
    val symbol = "IBIT"  // Asset to test
    val startDate = "2024-01-01"
    val endDate = "2025-03-18"
    val timeframe = "1Hour"
    
    println("Testing strategies on $symbol from $startDate to $endDate ($timeframe timeframe)")
    val ohlc = AlpacaMarketData.getOHLCData(symbol, startDate, endDate, timeframe)
    val priceData = ohlc.map { it.close }
    
    // Create base portfolio for all strategies
    val basePortfolio = Portfolio(
        positions = listOf(),
        cashPosition = CashPosition("USD", BigDecimal(100))
    )

    // Define all strategies to test
    val strategies = listOf(
        Strategy(
            startingPortfolio = basePortfolio,
            indicator = BottlePatternIndicator,
            instrument = symbol,
            exitStrategy = ExitStrategy.INDICATOR_SIGNAL,
            maxPositionSize = BigDecimal(5)
        ) to "Bottle Pattern (Trend Following)",

        Strategy(
            startingPortfolio = basePortfolio,
            indicator = EuphoriaPatternIndicator,
            instrument = symbol,
            exitStrategy = ExitStrategy.INDICATOR_SIGNAL,
            maxPositionSize = BigDecimal(5)
        ) to "Euphoria Pattern (Contrarian)",

        Strategy(
            startingPortfolio = basePortfolio,
            indicator = EngulfingPatternIndicator,
            instrument = symbol,
            exitStrategy = ExitStrategy.INDICATOR_SIGNAL,
            maxPositionSize = BigDecimal(5)
        ) to "Engulfing Pattern (Contrarian)",

        Strategy(
            startingPortfolio = basePortfolio,
            indicator = BottlePatternIndicator,
            instrument = symbol,
            profitTarget = 0.05,
            stopLoss = 0.02,
            exitStrategy = ExitStrategy.PROFIT_LOSS_TARGETS,
            maxPositionSize = BigDecimal(5)
        ) to "Bottle Pattern with 5% Profit / 2% Stop Loss",

        Strategy(
            startingPortfolio = basePortfolio,
            indicator = BottlePatternIndicator,
            instrument = symbol,
            profitTarget = 0.10,
            stopLoss = 0.05,
            exitStrategy = ExitStrategy.BOTH,
            maxPositionSize = BigDecimal(5)
        ) to "Bottle Pattern with Both (10% Profit / 5% Stop Loss)"
    )

    // Test each strategy and collect metrics
    val metrics = strategies.map { (strategy, name) ->
        println("\nTesting strategy: $name")
        val results = Backtest.runSingleAssetBackTest(strategy, ohlc)
        Performance.calculateMetrics(results, priceData, name)
    }

    // Print comparison of all strategies
    val assetReturn = if (priceData.size >= 2) {
        (priceData.last() - priceData.first()) / priceData.first()
    } else 0.0
    
    Performance.printStrategyComparison(metrics, assetReturn)

    // Visualize the best performing strategy
    val bestStrategy = metrics.maxByOrNull { it.outperformance }
    if (bestStrategy != null) {
        println("\nVisualizing best strategy: ${bestStrategy.strategyName}")
        val bestStrategyIndex = metrics.indexOf(bestStrategy)
        val results = Backtest.runSingleAssetBackTest(strategies[bestStrategyIndex].first, ohlc)
        val timestamps = ohlc.map { it.businessTime }
        ChartVisualizer.visualizeResults(results, priceData, timestamps)
    }
}
