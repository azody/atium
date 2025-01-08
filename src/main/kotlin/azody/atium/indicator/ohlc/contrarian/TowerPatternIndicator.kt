package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Tower Pattern
 * - Multi candle trend
 *     - Generally 5 candles
 *     - Signals the end of a gradual trend
 * - Variations
 *     - Can vary the number of candles in the middle
 *
 * Body Size
 * - Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
 * - Use body = 50 for BTCUSD
 * - Use body = 10 for ETHUSD
 * - Use body = 2 for XAUUSD
 * - Use body = 10 for SP500m, UK100
 */
object TowerPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. Starts with a bearish candle
     *      2. Followed by 3 smaller candles
     *      3. Middle of the three must be slightly lower than the other two
     *      4. Last, a normal-sized bullish candlestick
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 4]
            val secondCandle = data[i - 3]
            val thirdCandle = data[i - 2]
            val fourthCandle = data[i - 1]
            val fifthCandle = data[i]

            val minBodySize = options[OHLCParameter.MIN_BODY_HEIGHT.toString()]!! // TODO: Handle Error

            return firstCandle.isBearish() &&
                firstCandle.body() > minBodySize &&
                thirdCandle.low < fourthCandle.low &&
                thirdCandle.low < secondCandle.low &&
                fifthCandle.isBullish() &&
                fifthCandle.body() > minBodySize
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Pattern
     *      1. Starts with a bullish candle
     *      2. Followed by 3 smaller candles
     *      3. Middle of the three must be higher than the other two
     *      4. Lastly, a normal-sized bearish candlestick
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 4]
            val secondCandle = data[i - 3]
            val thirdCandle = data[i - 2]
            val fourthCandle = data[i - 1]
            val fifthCandle = data[i]

            val minBodySize = options[OHLCParameter.MIN_BODY_HEIGHT.toString()]!! // TODO: Handle Error

            return firstCandle.isBullish() &&
                firstCandle.body() > minBodySize &&
                thirdCandle.high > fourthCandle.high &&
                thirdCandle.high > secondCandle.high &&
                fifthCandle.isBearish() &&
                fifthCandle.body() > minBodySize
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
