package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Piercing Pattern
 */
object PiercingPatternIndicator : Indicator {
    /**
     * Bullish Pattern
     *      1. First a bearish candle
     *      2. Second a bullish candle
     *      3. Second candle opens at a gap lower and closes above the close of the previous candlestick
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val precedingCandle = data[i - 2]
            val firstCandle = data[i - 1]
            val secondCandle = data[i]
            return firstCandle.isBearish() &&
                secondCandle.isBullish() &&
                secondCandle.open < firstCandle.close &&
                secondCandle.close > firstCandle.close &&
                precedingCandle.isBearish()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Pattern
     *      1. First a bullish candle
     *      2. Second a bearish candle
     *      3. Second candle opens at a gap lower and closes below the close of the previous candlestick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val precedingCandle = data[i - 2]
            val firstCandle = data[i - 1]
            val secondCandle = data[i]
            return firstCandle.isBullish() &&
                secondCandle.isBearish() &&
                secondCandle.open > firstCandle.close &&
                secondCandle.close < firstCandle.close &&
                precedingCandle.isBullish()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
