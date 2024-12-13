package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.domain.Indicator
import azody.atium.ohlc.OHLC
import azody.atium.ohlc.isBearish
import azody.atium.ohlc.isBullish

/**
 * Bottle Indicator
 */
object BottlePatternIndicator : Indicator {
    /*
    Bullish Criteria for Bottle Pattern:
        1. Bullish Candle followed by another bullish candle
        2. Second Candle has no wick on the low side
        3. Second Candle can have a wick on the high side
        4. Second candle must open below the last candle's close
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean =
        try {
            val firstCandle = data[i - 1]
            val secondCandle = data[i]

            firstCandle.isBullish() &&
                secondCandle.isBullish() &&
                secondCandle.open == secondCandle.low &&
                secondCandle.open < firstCandle.close
        } catch (e: IndexOutOfBoundsException) {
            false
        }

    /*
    Bearish Criteria for Bottle Pattern:
        1. Bearish Candle followed by another bearish candle
        2. Second Candle has no wick on the high side
        3. Optional Wick on the low side
        4. Second candle must open above the last candle's close
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean =
        try {
            val firstCandle = data[i - 1]
            val secondCandle = data[i]

            firstCandle.isBearish() &&
                secondCandle.isBearish() &&
                secondCandle.open == secondCandle.high &&
                secondCandle.open > firstCandle.close
        } catch (e: IndexOutOfBoundsException) {
            false
        }
}
