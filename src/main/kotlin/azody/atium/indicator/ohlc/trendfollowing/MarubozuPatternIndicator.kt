package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Marubozu Pattern
 *
 * Characteristics
 * - Candles do not have any wicks
 *     - Bullish has the same open as the low and the close as the high price
 *     - Bearish has the same open as the high price and the same close as the low price
 * - Occurs during shorter time frames
 *     - 1 and 5 minute charts
 *
 * Reasoning
 * - Market Psychology
 *     - Maximum force of demand is when there is only upward movement
 * - Most powerful representation of bullish or bearish activity
 *
 * Nuances
 * - May be difficult in some markets
 *     - Currencies go out to 5 decimal places
 *     - Consider rounding in this scenario
 */
object MarubozuPatternIndicator : Indicator {
    /**
     * Bullish Indicator Criteria
     *      1. Open < Close - Bullish Candle
     *      2. High = Close - No Top Wick
     *      3. Low = Open   - No Bottom Wick
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val candle = data[i]

            return candle.isBullish() &&
                candle.high == candle.close &&
                candle.low == candle.open
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Indicator Criteria
     *      1. Close < Open - Bearish Candle
     *      2. High = Open  - No Top Wick
     *      3. Low = Close  - No Bottom Wick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val candle = data[i]

            return candle.isBearish() &&
                candle.high == candle.open &&
                candle.low == candle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
