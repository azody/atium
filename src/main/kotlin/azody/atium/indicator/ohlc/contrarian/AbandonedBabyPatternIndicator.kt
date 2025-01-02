package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Abandoned Baby Pattern
 *  - Extremely rare configuration
 *
 * Rounding
 *  - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 *  - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100
 */
object AbandonedBabyPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First a bearish candle
     *      2. Second a doji that gaps lower
     *      3. Third a bullish candlestick
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
                secondCandle.isDoji() &&
                secondCandle.high < firstCandle.low &&
                secondCandle.high < thirdCandle.low &&
                thirdCandle.isBullish()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     *  Bearish Criteria
     *      1. First a bullish candle
     *      2. Second a doji that gaps higher
     *      3. Third a bearish candlestick
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
                secondCandle.isDoji() &&
                secondCandle.low > firstCandle.high &&
                secondCandle.low > thirdCandle.high &&
                thirdCandle.isBearish()
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
