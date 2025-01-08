package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Hammer Pattern
 * - One of 4 similar patterns
 *     - Shooting Star, hanging Man, Hammer and Inverted Hammer
 * - Single candle configuration
 * - Logic
 *     - After shaping extreme lows, the buyers have managed to close higher than the open price
 *
 * Max candle height guide
 *  - Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
 *  - Use body = 50 for BTCUSD
 *  - Use body = 10 for ETHUSD
 *  - Use body = 2 for XAUUSD
 *  - Use body = 5 for SP500m, UK100
 *
 * Min Candle Wick height guide
 *  - Use wick = 0.0002 for EURUSD, USDCHF, GBPUSD, USDCAD
 *  - Use wick = 10 for BTCUSD
 *  - Use wick = 2 for ETHUSD
 *  - Use wick = 0.5 for XAUUSD
 *  - Use wick = 3 for SP500m, UK100
 *
 * Rounding
 *  - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 *  - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100
 */
object HammerPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. A bullish candlestick with a long low wick and no high wick
     *      2. It also must have a relatively small body
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

            val minWickSize = options[OHLCParameter.WICK_SIZE.toString()]!! // TODO: Handle Error Better
            val maxBodySize = options[OHLCParameter.MAX_BODY_HEIGHT.toString()]!! // TODO: Handle Error Better

            return firstCandle.isBearish() &&
                secondCandle.isBullish() &&
                thirdCandle.isBullish() &&
                secondCandle.close == secondCandle.high &&
                secondCandle.close - secondCandle.open <= maxBodySize &&
                secondCandle.open - secondCandle.low >= minWickSize
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     *  Bearish Criteria
     *      1. A long high wick and no low wick
     *      2. It also must have a relatively small body
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

            val minWickSize = options[OHLCParameter.WICK_SIZE.toString()]!! // TODO: Handle Error Better
            val maxBodySize = options[OHLCParameter.MAX_BODY_HEIGHT.toString()]!! // TODO: Handle Error Better

            return firstCandle.isBullish() &&
                secondCandle.isBearish() &&
                thirdCandle.isBearish() &&
                secondCandle.close == secondCandle.low &&
                secondCandle.open - secondCandle.close <= maxBodySize &&
                secondCandle.high - secondCandle.open >= minWickSize
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
