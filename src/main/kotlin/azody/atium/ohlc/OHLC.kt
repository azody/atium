package azody.atium.ohlc

data class OHLC(
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
)

fun OHLC.isBullish(): Boolean = close > open

fun OHLC.isBearish(): Boolean = close < open
