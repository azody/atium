@file:Suppress("ktlint:standard:no-wildcard-imports")

package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.domain.*

/**
 * H Pattern
 */
object HPatternIndicator : Indicator {
    /**
     *  Bullish Indicator Criteria
     *      1. First candle is bullish
     *      2. Second candle is an indecision
     *          - Open = Close
     *      3. Third candle is bullish
     *      4. Third close price must be higher than the indecisions close
     *      5. Third low must be higher than the low of the indecision candle
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

            return firstCandle.isBullish() &&
                secondCandle.isIndecision() &&
                thirdCandle.isBullish() &&
                thirdCandle.close > secondCandle.close &&
                thirdCandle.low > secondCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     *  Bearish Indicator Criteria
     *      1. First candle is bearish
     *      2. Next candlie is an indecision
     *          - Open = close
     *      3. Third candle is bearish
     *      4. Third close must be lower than the indecision candle close
     *      5. Third high must be lower than the high of the indecision candle
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

            return firstCandle.isBearish() &&
                secondCandle.isIndecision() &&
                thirdCandle.isBearish() &&
                thirdCandle.close < secondCandle.close &&
                thirdCandle.high < secondCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
