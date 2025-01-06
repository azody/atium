package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Engulfing Pattern
 * - Mirror of the Harami Pattern
 *     - Both are contrarian
 */
object EngulfingPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First a bearish candle
     *      2. Then a bullish candle
     *          - Bullish candle completely engulfs the first candlestick in a strict way
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
                secondCandle.close > firstCandle.open
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First a bullish candle
     *      2. Then a bearish candle
     *          - Bearish candle completely engulfs the first candlestick in a strict way
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
                secondCandle.close < firstCandle.open
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
