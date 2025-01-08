package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Doppelganger Pattern
 * - Round Data
 *     -  data = rounding(data, 4) # Put 0 instead of 4 as of pair 4
 * - Optional condition
 *     - Confirm bullish reversal
 *         - Next candle surpasses similar highs
 *     - Confirm bearish reversal
 *         - Next candle surpasses similar lows
 */
object DoppelgangerPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First a bearish candle
     *      2. Second candle close is less than first candle open
     *      3. Third high equals second high
     *      4. Third low equals second low
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
                secondCandle.close < firstCandle.open &&
                thirdCandle.high == secondCandle.high &&
                thirdCandle.low == secondCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First candle is bullish
     *      2. Second candle close is greater than first candle open
     *      3. Third high equals second high
     *      4. Third low equals second low
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
                secondCandle.close > firstCandle.open &&
                thirdCandle.high == secondCandle.high &&
                thirdCandle.low == secondCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
