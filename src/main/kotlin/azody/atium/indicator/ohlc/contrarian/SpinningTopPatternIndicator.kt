package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Spinning Top Pattern
 * - Resembles Doji Pattern but it less strict
 * - Hints at a rise in Volatility
 *
 * Max Body Size
 * - Use body = 0.0003 for EURUSD, USDCHF, GBPUSD, USDCAD
 * - Use body = 50 for BTCUSD
 * - Use body = 10 for ETHUSD
 * - Use body = 2 for XAUUSD
 * - Use body = 5 for SP500m, UK100
 *
 * Min Wick Size
 * - Use wick = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
 * - Use wick = 20 for BTCUSD
 * - Use wick = 5 for ETHUSD
 * - Use wick = 1 for XAUUSD
 * - Use wick = 3 for SP500m, UK100
 */
object SpinningTopPatternIndicator : Indicator {
    /**
     * Bullish Pattern
     *      1. First a bearish candle
     *      2. Second a small bodied candle with long wick (highs and low)
     *      3. Third a bullish candle
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

            val minWickSize = options.get(OHLCParameter.WICK_SIZE.toString())!! // TODO: Handle Error Better
            val maxBodySize = options.get(OHLCParameter.MAX_BODY_HEIGHT.toString())!! // TODO: Handle Error Better

            return firstCandle.isBearish() &&
                secondCandle.isBullish() &&
                thirdCandle.isBullish() &&
                secondCandle.close - secondCandle.open <= maxBodySize &&
                secondCandle.open - secondCandle.low >= minWickSize &&
                secondCandle.high - secondCandle.close >= minWickSize
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Pattern
     *      1. First a bullish candle
     *      2. Second a small bodied candle with long wicks (highs and lows)
     *      3. Third a bearish candle
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

            val minWickSize = options.get(OHLCParameter.WICK_SIZE.toString())!! // TODO: Handle Error Better
            val maxBodySize = options.get(OHLCParameter.MAX_BODY_HEIGHT.toString())!! // TODO: Handle Error Better

            return firstCandle.isBullish() &&
                secondCandle.isBearish() &&
                thirdCandle.isBearish() &&
                secondCandle.open - secondCandle.close <= maxBodySize &&
                secondCandle.open - secondCandle.low >= minWickSize &&
                secondCandle.high - secondCandle.close >= minWickSize
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
