package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Mirror Pattern
 */
object MirrorPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First Candle stick is bearish
     *      2. Next two candlesticks that have the same highs and lows
     *      3. Last candlestick is bullish
     *        - Must have a high price equal to the high price of the first candlestick
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 3]
            val secondCandle = data[i - 2]
            val thirdCandle = data[i - 1]
            val fourthCandle = data[i]

            return firstCandle.isBearish() &&
                secondCandle.high == thirdCandle.high &&
                secondCandle.low == thirdCandle.low &&
                fourthCandle.isBullish() &&
                fourthCandle.high == firstCandle.high
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First candle is bullish
     *      2. Next two candlesticks have the same highs and lows
     *      3. Last candlestick is bearish
     *          - Must have a low price equal to the low price of the first candlestick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 3]
            val secondCandle = data[i - 2]
            val thirdCandle = data[i - 1]
            val fourthCandle = data[i]

            return firstCandle.isBullish() &&
                secondCandle.high == thirdCandle.high &&
                secondCandle.low == thirdCandle.low &&
                fourthCandle.isBearish() &&
                fourthCandle.low == firstCandle.low
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
