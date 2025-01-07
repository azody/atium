package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Inside Up/Down Pattern
 * - Signals the end of an initial move
 * Max body
 * - Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
 * - Use body = 50 for BTCUSD
 * - Use body = 10 for ETHUSD
 * - Use body = 2 for XAUUSD
 * - Use body = 10 for SP500m, UK100
 */
object InsideUpDownPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First a bearish candle
     *      2. Second a smaller bullish candle
     *      3. Second candle body must be contained in the bearish candle
     *      4. Third a bullish candle
     *      5. Third candle must surpass the open of the first candle
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 2]
            val secondCandle = data[i - 1] // Hammer Candle
            val thirdCandle = data[i]

            val maxBodySize = options.get(OHLCParameter.MAX_BODY_HEIGHT.toString())!! // TODO: Handle Error Better

            return firstCandle.isBearish() &&
                secondCandle.isBullish() &&
                secondCandle.body() <= maxBodySize &&
                secondCandle.close < firstCandle.open &&
                secondCandle.open > firstCandle.close &&
                thirdCandle.isBullish() &&
                thirdCandle.close > firstCandle.open
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. First a bullish candle
     *      2. Second a smaller bearish candle
     *      3. Second candle body must be contained in the bullish candle
     *      4. Third a bearish candle
     *      5. Third candle must surpass the open of the first bullish candleA
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 2]
            val secondCandle = data[i - 1] // Hammer Candle
            val thirdCandle = data[i]

            val maxBodySize = options.get(OHLCParameter.MAX_BODY_HEIGHT.toString())!! // TODO: Handle Error Better

            return firstCandle.isBullish() &&
                secondCandle.isBearish() &&
                secondCandle.body() <= maxBodySize &&
                secondCandle.open < firstCandle.close &&
                secondCandle.close > firstCandle.open &&
                thirdCandle.isBearish() &&
                thirdCandle.close < firstCandle.open
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
