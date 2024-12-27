package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.*

/**
 * Slingshot Pattern
 * - Deals with a breakout system that confirms the new trend
 * - Four candlestick trend detection system that uses pullback method to identify a breakout
 */
object SlingshotPatternIndicator : Indicator {
    /**
     * Bullish Indicator Criteria
     *      1. Bullish first candle
     *      2. Followed by a higher one confirming a bullish bias
     *      3. Third candle not surpassing the high of the second
     *      4. Final candle that must have a low at or below the high of the first candlestick and a close higher than that of the second candlestick
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 3]
            val secondCandle = data[i - 2]
            val thirdCandle = data[i - 1]
            val fourthCandle = data[i - 0]
            return fourthCandle.close > thirdCandle.high &&
                fourthCandle.close > secondCandle.high &&
                fourthCandle.low <= firstCandle.high &&
                fourthCandle.isBullish() &&
                thirdCandle.close >= firstCandle.high &&
                secondCandle.isBullish() &&
                secondCandle.close > firstCandle.high &&
                thirdCandle.high <= secondCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Indicator Criteria
     *      1. Bearish first candle
     *      2. Followed by a lower ont confirming a bearish bias
     *      3. Third candle not breaking the low of the second candle
     *      4. Final candlestick that has a high at or above the low of the first candsle and a close lower than the low of the second candlestick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 3]
            val secondCandle = data[i - 2]
            val thirdCandle = data[i - 1]
            val fourthCandle = data[i]
            return fourthCandle.close < thirdCandle.low &&
                fourthCandle.close < secondCandle.low &&
                fourthCandle.high >= firstCandle.low &&
                thirdCandle.high <= firstCandle.high &&
                secondCandle.close <= firstCandle.low &&
                secondCandle.isBearish() &&
                secondCandle.close < firstCandle.low &&
                thirdCandle.low >= secondCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
