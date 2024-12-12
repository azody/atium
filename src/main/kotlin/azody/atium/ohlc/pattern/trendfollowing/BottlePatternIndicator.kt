package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.ohlc.OHLC
import azody.atium.domain.Indicator

/**
 * Bottle Indicator
 */
object BottlePatternIndicator : Indicator {

    /*
    Bullish Criteria for Bottle Pattern:
        1. Bullish Candle followed by another bullish candle
        2. No wick on the low side
        3. Wick on the high side
        4. Second candle must open below the last candle's close
    */
    override fun bullIndicator(data: List<OHLC>, i: Int, option: Map<String, Double>): Boolean {
        return try {
            val current = data[i]
            val previous = data[i - 1]
            current.close < current.open &&
                    current.open == current.high &&
                    previous.close < previous.open &&
                    current.open > previous.close
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }

    /*
    Bearish Criteria for Bottle Pattern:
        1. Bearish Candle followed by another bearish candle
        2. No wick on the high side
        3. Wick on the low side
        4. Second candle must open above the last candle's close
    */
    override fun bearIndicator(data: List<OHLC>, i: Int, option: Map<String, Double>): Boolean {
        return try {
            val current = data[i]
            val previous = data[i - 1]
            current.close < current.open &&
                    current.open == current.high &&
                    previous.close < previous.open &&
                    current.open > previous.close
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }
}