package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.domain.*

/**
 * Three Candles Pattern
 *
 * If the close price is lower than the open price
 *     And the high price equals the open price
 *     And the low price equals the close price
 *     Then add a bearish indicator
 *
 * Min Body Matrix
 * | Asset   | Body   | Type   |
 * -----------------------------
 * | EURUSD  | 0.0005 | USD    |
 * | USDCHF  | 0.0005 | CHF    |
 * | GBPUSD  | 0.0005 | USD    |
 * | USDCAD  | 0.0005 | CAD    |
 * | BTCUSD  | 50     | USD    |
 * | ETHUSD  | 10     | USD    |
 * | Gold    | 2      | USD    |
 * | SP500   | 10     | Points |
 * | FTSE100 | 10     | Points |
 *
 * Characteristics
 * - Three White Soldiers
 *     - Bullish version
 *     - Three big bulilsh candles in a row with each having a close higher than the previous
 * - Three Black Crows
 *     - Bearish version
 *     - Three big bearish candles in a row with each having a close lower than the previous
 * Reasoning
 * - Market Psychology
 *     - Herding: Markets follow trend because others are doing so
 *     - Humans are attracted to confidence and strength
 * Nuances
 * - May be difficult in some markets
 *     - Currencies go out to 5 decimal places
 *     - Consider rounding in this scenario
 */
object ThreeCandlesPatternIndicator : Indicator {
    /**
     * Bullish Indicator Criteria
     *      1. The last three close prices are each greater than the close prices preceeding them
     *      2. Each candlestick respects a minimum body size
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val previousCandle = data[i - 3] // This candle is not involved in the pattern, but you need its close
            val firstCandle = data[i - 2]
            val secondCandle = data[i - 1]
            val thirdCandle = data[i]
            val minBodySize = options[OHLCParameter.MIN_BODY_HEIGHT.toString()]!! // TODO: Exception Handling
            return firstCandle.body() > minBodySize &&
                secondCandle.body() > minBodySize &&
                thirdCandle.body() > minBodySize &&
                thirdCandle.close > secondCandle.close &&
                secondCandle.close > firstCandle.close &&
                firstCandle.close > previousCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Indicator Criteria
     *      1. The last three close prices are each lower than the close prices preceeding them
     *      2. Each candlestick respects a minimum body size
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val previousCandle = data[i - 3] // This candle is not involved in the pattern, but you need its close
            val firstCandle = data[i - 2]
            val secondCandle = data[i - 1]
            val thirdCandle = data[i]
            val minBodySize = options[OHLCParameter.MIN_BODY_HEIGHT.toString()]!! // TODO: Exception Handling
            return firstCandle.body() > minBodySize &&
                secondCandle.body() > minBodySize &&
                thirdCandle.body() > minBodySize &&
                thirdCandle.close < secondCandle.close &&
                secondCandle.close < firstCandle.close &&
                firstCandle.close < previousCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
