package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Harami Pattern
 * - Contrarian Pttern where the body of the second candle is englobed by the body of the first candle
 * - Difficult to find and backtest
 */
object HaramiStrictPatternIndicator : Indicator {
    /**
     * Bullish Criteria
     *      1. First candle is a bearish candle
     *      2. And the second candle is bullish
     *      3. And the second candles high price is lower than the open price of the first candle
     *      4. And the second canles low price is higher than the close price of the previous candle
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
                secondCandle.high < firstCandle.open &&
                secondCandle.low > firstCandle.close
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     * Bearish Criteria
     *      1. The first candle is bullish
     *      2. And the second candle is bearish
     *      3. And the second candles has a lower high price than the close price of the first candle
     *      4. And the second candles low price is higher than the open price of the first candle
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
                secondCandle.high < firstCandle.close &&
                secondCandle.low > firstCandle.open
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
