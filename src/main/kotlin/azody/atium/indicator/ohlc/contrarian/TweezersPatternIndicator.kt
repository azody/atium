package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Tweezers Pattern
 * my_data = rounding(my_data, 5) - for forex, use something smarter elsewhere
 */
object TweezersPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First a bearish candle
     *      2. Then a bearish candlestick
     *      3. Then a bullish candlestick
     *      4. The second bearish candlestick shares a low with the bullish candlestick
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 2]
            val secondCandle = data[i - 1]
            val thirdCandle = data[i]
            return firstCandle.isBearish() &&
                secondCandle.isBearish() &&
                thirdCandle.isBullish() &&
                secondCandle.low == thirdCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First a bullish candle
     *      2. Then a bullish candlestick
     *      3. Then a bearish candlestick
     *      4. The second bullish candlestick shares a high with the bearish candlestick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 2]
            val secondCandle = data[i - 1]
            val thirdCandle = data[i]
            return firstCandle.isBullish() &&
                secondCandle.isBullish() &&
                thirdCandle.isBearish() &&
                secondCandle.high == thirdCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
