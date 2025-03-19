package azody.atium.visualization

import azody.atium.backtest.BackTestResults
import azody.atium.domain.Trade
import azody.atium.domain.TradeType
import azody.atium.domain.getTotalValue
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries
import org.knowm.xchart.style.Styler
import org.knowm.xchart.style.markers.SeriesMarkers
import java.awt.Color
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object ChartVisualizer {
    fun visualizeResults(results: BackTestResults, priceData: List<Double>, timestamps: List<Long>) {
        // Create the chart
        val chart = XYChartBuilder()
            .width(1200)
            .height(800)
            .title("Backtest Results")
            .xAxisTitle("Date")
            .yAxisTitle("Price / Portfolio Value")
            .build()

        // Customize the chart
        chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line
        chart.styler.isChartTitleVisible = true
        chart.styler.legendPosition = Styler.LegendPosition.OutsideE
        chart.styler.markerSize = 8
        chart.styler.plotBackgroundColor = Color.WHITE
        chart.styler.chartBackgroundColor = Color.WHITE
        chart.styler.isPlotGridLinesVisible = true
        chart.styler.isPlotGridVerticalLinesVisible = true

        // Convert timestamps to dates for X-axis
        val dates = timestamps.map { 
            Date.from(Instant.ofEpochMilli(it))
        }

        // Add price series
        val priceSeries = chart.addSeries(
            "Price", 
            dates,
            priceData
        )
        priceSeries.lineColor = Color.BLUE
        priceSeries.marker = SeriesMarkers.NONE

        // Add portfolio value series
        val portfolioValues = results.portfolioSeries.entries
            .sortedBy { it.key }
            .map { it.value.getTotalValue().toDouble() }
        val portfolioDates = results.portfolioSeries.keys
            .sorted()
            .map { Date.from(Instant.ofEpochMilli(it)) }
        val portfolioSeries = chart.addSeries(
            "Portfolio Value",
            portfolioDates,
            portfolioValues
        )
        portfolioSeries.lineColor = Color.GREEN
        portfolioSeries.marker = SeriesMarkers.NONE

        // Add buy signals
        val buyTrades = results.tradeSeries.values.flatten()
            .filter { it.type == TradeType.BUY }
        if (buyTrades.isNotEmpty()) {
            addTradeMarkers(chart, buyTrades, "Buy Signals", Color.GREEN, SeriesMarkers.TRIANGLE_UP)
        }

        // Add sell signals
        val sellTrades = results.tradeSeries.values.flatten()
            .filter { it.type == TradeType.SELL }
        if (sellTrades.isNotEmpty()) {
            addTradeMarkers(chart, sellTrades, "Sell Signals", Color.RED, SeriesMarkers.TRIANGLE_DOWN)
        }

        // Display the chart
        SwingWrapper(chart).displayChart()
    }

    private fun addTradeMarkers(
        chart: org.knowm.xchart.XYChart,
        trades: List<Trade>,
        seriesName: String,
        color: Color,
        marker: org.knowm.xchart.style.markers.Marker
    ) {
        val tradeDates = trades.map { Date.from(Instant.ofEpochMilli(it.businessTime)) }
        val tradePrices = trades.map { it.price.toDouble() }
        
        val series = chart.addSeries(
            seriesName,
            tradeDates,
            tradePrices
        )
        series.lineColor = color
        series.marker = marker
        series.xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    }

    private fun formatDate(timestamp: Long): String {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        ).toString()
    }
} 