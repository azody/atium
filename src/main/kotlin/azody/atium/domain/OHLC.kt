package azody.atium.domain

import kotlin.math.abs

data class OHLC(
    val businessTime: Long, // In candles business time is the close time
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double = 0.0,
    val volatilityIndicators: Map<VolatilityIndicator, Double> = emptyMap(),
)

fun OHLC.isBullish(): Boolean = close > open

fun OHLC.isBearish(): Boolean = close < open

fun OHLC.isDoji(): Boolean = open == close

fun OHLC.embeds(ohlc: OHLC): Boolean = ohlc.high < this.high && ohlc.low > this.low

fun OHLC.height(): Double = high - low

fun OHLC.body(): Double = abs(close - open)

enum class OHLCParameter {
    MAX_BODY_HEIGHT,
    MIN_BODY_HEIGHT,
    WICK_SIZE,
}
