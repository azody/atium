package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.*

/**
 * Doji Pattern
 * - Most well known and intuitive candle pattern
 * - Rational
 *     - Doji candle is the equiliibrium between supply and demand before a reversal
 * - Variants
 *     - Dragonfly Doji: High price equal to the close and open price
 *     - Gravestone Doji: Low price is equal to close and open price
 *     - Flat Doji: Occurs when open, high, low and close are equal
 *         - Indicates low volume and liquidity
 *     - Double Doji: Two Dojis in a row
 *         - Two Dojis are not worth more than one
 *     - Tri Star Doji: Three Dojis in a row where the middle Doji gaps over the other two
 * - Results
 *     - Common pattern
 *     - Generally not predictive
 *     - Works best in sideways marketsa
 * Rounding
 *     - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 *     - vsUse my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100
 */
object DojiPatternIndicator : Indicator {
    /**
     *  Bullish Criteria
     *      1. First a bearish candle
     *      2. Then a Doji Candle
     *      3. Finally, a bullish candle
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
                thirdCandle.isBullish() &&
                thirdCandle.close > secondCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

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
                thirdCandle.isBearish() &&
                thirdCandle.close < secondCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
