@file:Suppress("ktlint:standard:no-wildcard-imports")

package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.domain.*

/**
 * Hikkake pattern
 *
 * Reasoning
 * - Market Psychology
 *     - Historical Bear Trap
 */
object HikkakePatternIndicator : Indicator {
    /**
     *  Bull Indicator Criteria
     *      1. Start with a bullish candlestick followed by a bearish candlestick completely embedded in the first one
     *      2.  Then 2 candlesticks must appear with a high that does surpass the second candles high
     *      3. Finally, a big bullish candlestick appears with a close that surpasses the high of the second candlestick
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
                thirdCandle.high <= secondCandle.high &&
                fourthCandle.high <= secondCandle.high &&
                fifthCandle.isBullish() &&
                fifthCandle.close > secondCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     *  Bearish Indicator Criteria
     *      1. Start with a bearish candlestick followed by a bullish candlestick completely embedded in the first one
     *      2. Then 2 candlesticks must appear with a low that does not surpass the second candles low
     *      3. Finally, a big bearish candlestick appears with a close that surpasses the low of the second candlestick
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
                thirdCandle.low >= secondCandle.low &&
                fourthCandle.low >= secondCandle.low &&
                fifthCandle.isBearish() &&
                fifthCandle.close < secondCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
