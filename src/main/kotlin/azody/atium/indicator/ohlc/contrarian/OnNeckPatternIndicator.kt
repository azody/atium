package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * On Neck Pattern
 */
object OnNeckPatternIndicator : Indicator {
    /**
     * Bullish Pattern
     *      1. First, a bearish candlestick
     *      2. And then a second bullish candlestick
     *      3. The second candlestick opens at a gap lower
     *      4. The second candlestick closes exactly at the close price of the first candlestick
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 1]
            val secondCandle = data[i]

            return firstCandle.isBearish() &&
                secondCandle.isBullish() &&
                secondCandle.open < firstCandle.close &&
                secondCandle.close == firstCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Pattern
     *      1. First, a bullish candlestick
     *      2. And then a second bearish candlestick
     *      3. The second candlestick opens at a gap higher
     *      4. The second candlestick closes exactly at the close price of the first candlestick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 1]
            val secondCandle = data[i]

            return firstCandle.isBullish() &&
                secondCandle.isBearish() &&
                secondCandle.open > firstCandle.close &&
                secondCandle.close == firstCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
