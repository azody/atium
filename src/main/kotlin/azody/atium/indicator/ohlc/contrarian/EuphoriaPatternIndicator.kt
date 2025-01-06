package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Euphoria Pattern
 * - Very similar to Three Candles pattern
 *     - Additional condition makes it contrarian
 * - Rounding
 *     - data = rounding(data, 4)
 * - Trades best in a sideways market
 * - Ideal for short moves
 */
object EuphoriaPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. Three successive bearish candles
     *      2. Each candles body must be greater than the preceding candles
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
                thirdCandle.isBearish() &&
                firstCandle.body() < secondCandle.body() &&
                secondCandle.body() < thirdCandle.body()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

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
                thirdCandle.isBullish() &&
                firstCandle.body() < secondCandle.body() &&
                secondCandle.body() < thirdCandle.body()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
