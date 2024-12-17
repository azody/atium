@file:Suppress("ktlint:standard:no-wildcard-imports")

package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.domain.*

/**
 * Quintuplets Pattern
 *
 *
 * Max Body Matrix
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
 * Psychology
 *     - Herding and failure to react
 *     - Usually seen in markets where the price action remains in a low volatility environment but a clear trend can be seen
 *
 */
object QuintupletsPatternIndicator : Indicator {
    /**
     * Bull Indicator Criteria
     *      1. The last 5 close prices are each greater than their open price
     *      2. The last 5 close prices are greater than the close prices proceeding them
     *      3. Each candlestick respects a maximum body size
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
            val maxBody = options[OHLCParameter.MAX_BODY_HEIGHT.toString()]!!
            return firstCandle.isBullish() &&
                firstCandle.body() < maxBody &&
                secondCandle.isBullish() &&
                secondCandle.close > firstCandle.close &&
                secondCandle.body() < maxBody &&
                thirdCandle.isBullish() &&
                thirdCandle.close > secondCandle.close &&
                thirdCandle.body() < maxBody &&
                fourthCandle.isBullish() &&
                fourthCandle.close > thirdCandle.close &&
                fourthCandle.body() < maxBody &&
                fifthCandle.isBullish() &&
                fifthCandle.close > fourthCandle.close &&
                fifthCandle.body() < maxBody
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bear Indicator Criteria
     *      1. The last 5 close prices are each lower than their open price
     *      2. The last 5 close prices are lower than the close prices proceeding them
     *      3. Each candlestick respects a maximum body size
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
            val maxBody = options[OHLCParameter.MAX_BODY_HEIGHT.toString()]!!
            return firstCandle.isBearish() &&
                firstCandle.body() < maxBody &&
                secondCandle.isBearish() &&
                secondCandle.close < firstCandle.close &&
                secondCandle.body() < maxBody &&
                thirdCandle.isBearish() &&
                thirdCandle.close < secondCandle.close &&
                thirdCandle.body() < maxBody &&
                fourthCandle.isBearish() &&
                fourthCandle.close < thirdCandle.close &&
                fourthCandle.body() < maxBody &&
                fifthCandle.isBearish() &&
                fifthCandle.close < fourthCandle.close &&
                fifthCandle.body() < maxBody
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
