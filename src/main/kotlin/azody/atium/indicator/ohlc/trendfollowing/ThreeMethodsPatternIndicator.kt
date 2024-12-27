package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.*

/**
 * Three Methods Pattern
 *
 * Reasoning
 * - Market Psychology
 *     - Stabilizes price through correction or consolidation as traders close out of positions following a big bear/bull candle
 * Nuances
 * - Rare signal
 * - Rarely useful when used on its own
 */
object ThreeMethodsPatternIndicator : Indicator {
    /**
     * Bullish Indicator Criteria
     *      1. First candlestick is a big bullish candle, followed by three smaller bodied bearish candlesticks and one final big bullish candle
     *      2. Three bearish candles are normally contained in the range of the first bullish candle
     *      3. Final bullish candle has a close higher than the first candles high
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

            return firstCandle.isBullish() &&
                secondCandle.isBearish() &&
                firstCandle.embeds(secondCandle) &&
                thirdCandle.isBearish() &&
                firstCandle.embeds(thirdCandle) &&
                fourthCandle.isBearish() &&
                firstCandle.embeds(fourthCandle) &&
                fifthCandle.isBullish() &&
                fifthCandle.close > firstCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Indicator Criteria
     *      1. First candlestick is a big bearish candle, followed by three smaller bodied bullish candlesticks and one final big bearish candle
     *      2. Three bullish candles are normally contained in the range of the first bearish candle
     *      3. Final bearish candle has a close lower than the first candles low
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

            return firstCandle.isBearish() &&
                secondCandle.isBullish() &&
                firstCandle.embeds(secondCandle) &&
                thirdCandle.isBullish() &&
                firstCandle.embeds(thirdCandle) &&
                fourthCandle.isBullish() &&
                firstCandle.embeds(fourthCandle) &&
                fifthCandle.isBearish() &&
                fifthCandle.close < firstCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
