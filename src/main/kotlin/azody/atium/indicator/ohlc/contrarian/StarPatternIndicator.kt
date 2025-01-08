package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Star Pattern
 * - 3 Candle configuration
 *     - Less common than others as it relies on a gap
 * - Logic
 *     - The market has made a perfectly shaped U Turn after discovering a new high/low
 * Rounding
 *  - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 *  - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100
 */
object StarPatternIndicator : Indicator {
    /**
     * Bullish Pattern (Morning Star)
     *      1. First, a bearish candle
     *      2. Second, a small bodied candle that gaps below it
     *      3. Does not matter if second candle bearish or bullish
     *      4. Third, a bullish candle which gaps higher than the middle candle
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
                secondCandle.close.coerceAtLeast(secondCandle.open) < thirdCandle.open &&
                secondCandle.close.coerceAtLeast(secondCandle.open) < firstCandle.close &&
                thirdCandle.isBullish()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     *  Bearish Pattern (Evening Star)
     *      1. First, a bullish candle
     *      2. Second, a small bodied candle that gaps above it
     *      3. Does not matter if second candle is bearish or bullish
     *      4. Third, a bearish candle which gaps lower than the middle candle
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
                secondCandle.close.coerceAtMost(secondCandle.open) > thirdCandle.open &&
                secondCandle.close.coerceAtMost(secondCandle.open) > firstCandle.close &&
                thirdCandle.isBearish()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
