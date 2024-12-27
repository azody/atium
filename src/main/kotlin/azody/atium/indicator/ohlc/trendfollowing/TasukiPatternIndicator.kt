package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Tasuki Pattern
 * Characteristics
 * - Gap is formed giving a continuation signal
 *     - Gaps are a discontinuation or a hole between to successive close prices, mainly caused by loq liquidity
 * - Types of Gaps
 *     - Common gaps: Occur in a sideways market
 *         - likely to be filled because of markets mean-reversion dynamic
 *     - Breakaway gaps: Generally resemble a common gap
 *         - Gap occurs above a graphical resistance or below a graphical support
 *     - Runaway gaps: Generally occur within the trend but confirm it more
 *         - Continuation Pattern
 *     - Exhaustion gaps: generally occur at the end of a trend and close to a support or resistance level
 *         - Reversal pattern
 *     - Impossible to know the type of gap ahead of time
 * - Bullish characteristic
 *     - Three candlesticks where first is bullish, second is bullish and the third is bearish but does not close below the close of the first
 * - Bearish Characteristic
 *     - Three candlesticks where first is bearish, second is bearish and the third is bullish but does not close above the close of the first
 * Reasoning
 * - Market Psychology
 *     - Not enough pressure to reach the previous threshold
 * Nuances
 * - Rarely occurs
 * - Intuitive but not necessarily accurate
 */
object TasukiPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. The close price from two periods ago is greater than the open price from two periods ago
     *      2. The open price from one period ago is greater than the close two period ago
     *      3. The close price from one period ago is greater than the close two periods ago
     *      4. The close price from one period ago is greater than the open price from one period ago
     *      5. The current close price is greater than the close price two periods ago
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
                secondCandle.isBullish() &&
                secondCandle.open > firstCandle.close &&
                thirdCandle.isBearish() &&
                thirdCandle.close < secondCandle.open &&
                thirdCandle.close > firstCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Indicator
     *      1. The close price from two periods ago is lower than the open price from two periods ago
     *      2. The open price from one period ago is lower than the close two periods ago
     *      3. The close price from one period is greater than the open price from one period ago
     *      4. The current close price is greater than the close price two periods ago
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
                secondCandle.isBearish() &&
                secondCandle.open < firstCandle.close &&
                thirdCandle.isBullish() &&
                thirdCandle.close > secondCandle.open &&
                thirdCandle.close < firstCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
