package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Barrier Pattern
 * - Round Data
 *     my_data = rounding(data, 4) # Put 0 instead of 4 as of pair 4
 *
 * - Optional extra parameter
 *     - Bullish: bullish candle must have a close higher than the high price of the middle candle
 *     - Bearish: bearish candle must have a close price lower than the low price of the middle candlestick
 */
object BarrierPatternIndicator : Indicator {
    /**
     *  Bullish Criteria
     *      1. First 2 candles are bearish
     *      2. Third candle is bullish
     *      3. Lows of all three candles is the same
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
                thirdCandle.isBullish() &&
                firstCandle.low == secondCandle.low &&
                secondCandle.low == thirdCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First 2 candles are bullish
     *      2. Third candle is bearish
     *      3. Highs of all three candles must be the same
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
                secondCandle.isBullish() &&
                thirdCandle.isBearish() &&
                firstCandle.high == secondCandle.high &&
                secondCandle.high == thirdCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
