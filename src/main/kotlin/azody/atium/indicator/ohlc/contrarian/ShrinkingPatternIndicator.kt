package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Shrinking Pattern
 */
object ShrinkingPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First candle must be bearish
     *      2. Following three candles may have any color but are shrinking in size every time
     *      3. Final candle must be bullish and must surpass the high of the second candle
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 4]
            val secondCandle = data[i - 3]
            val thirdCandle = data[i - 2]
            val fourthCandle = data[i - 1]
            val fifthCandle = data[i]

            return firstCandle.isBearish() &&
                secondCandle.body() < firstCandle.body() &&
                thirdCandle.body() < secondCandle.body() &&
                fourthCandle.body() < thirdCandle.body() &&
                fifthCandle.isBullish() &&
                fifthCandle.close > secondCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First candle must be bullish
     *      2. Following three candles may have any color but are shrinking in size everytime
     *      3. Final candle must be bearish and must break the low of the second candlestick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 4]
            val secondCandle = data[i - 3]
            val thirdCandle = data[i - 2]
            val fourthCandle = data[i - 1]
            val fifthCandle = data[i]

            return firstCandle.isBullish() &&
                secondCandle.body() < firstCandle.body() &&
                thirdCandle.body() < secondCandle.body() &&
                fourthCandle.body() < thirdCandle.body() &&
                fifthCandle.isBearish() &&
                fifthCandle.close < secondCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
