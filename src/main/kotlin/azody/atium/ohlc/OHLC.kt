package azody.atium.ohlc

import azody.atium.domain.VolatilityIndicator
import kotlin.math.abs

data class OHLC(
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volatilityIndicators: Map<VolatilityIndicator, Double> = emptyMap(),
)

fun OHLC.isBullish(): Boolean = close > open

fun OHLC.isBearish(): Boolean = close < open

fun OHLC.isIndecision(): Boolean = open == close

fun OHLC.height(): Double = high - low

fun OHLC.body(): Double = abs(close - open)
