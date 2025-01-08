package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Stick Sandwich Pattern
 * - 3 candle contrarian configuration composed of candlsticks that alternate in color
 * - Logic
 *     - By failing to form new lows in the bullish scenario, the market may have found support
 */
object StickSandwichPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First a bearish candlestick
     *      2. Second a bullish candlestick that is smaller than the first candlestick
     *      3. Last, a bearish candlestick that is larger than the second candlestick
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
                secondCandle.isBullish() &&
                secondCandle.body() < firstCandle.body() &&
                thirdCandle.isBearish() &&
                thirdCandle.body() > secondCandle.body()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First a bullish candlestick
     *      2. Second a bearish candlestick that is smaller than the first candlestick
     *      3. Last, a bullish candlestick that is greater than the second
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
                secondCandle.isBearish() &&
                secondCandle.body() < firstCandle.body() &&
                thirdCandle.isBullish() &&
                thirdCandle.body() > secondCandle.body()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
