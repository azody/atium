package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.Indicator
import azody.atium.domain.OHLC
import azody.atium.domain.VolatilityIndicator
import azody.atium.domain.height
import azody.atium.domain.isBearish
import azody.atium.domain.isBullish

/**
 * Double Trouble Pattern
 *
 * Uses exogenous variables to be validated
 * - Borrow information from the Average True Range (ATR) to validate the signal on the pattern
 *
 * Psychology
 *     - Market euphoria
 *     - Indicator of a short squeeze
 */
object DoubleTroublePatternIndicator : Indicator {
    /**
     *  Bull Criteria
     *      1. 2 bullish candles with the first close price lower than the second close price
     *      2. 2nd candlestick must be at least double the size of the previous candlesticks 10 period ATR
     */
    override fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 1]
            val secondCandle = data[i]

            val firstCandleATR = firstCandle.volatilityIndicators[VolatilityIndicator.AVERAGE_TRUE_RANGE]!! // TODO: Better Error Handling

            return firstCandle.isBullish() &&
                secondCandle.isBullish() &&
                firstCandle.close < secondCandle.close &&
                secondCandle.height() > (2 * firstCandleATR)
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    /**
     *  Bear Criteria
     *      1. 2 bearish candles with the first close price higher than the second close price
     *      2. 2nd candlestick must also be at least double the size of the previous candlestick 10-period ATR
     */
    override fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double>,
    ): Boolean {
        try {
            val firstCandle = data[i - 1]
            val secondCandle = data[i]

            val firstCandleATR =
                firstCandle.volatilityIndicators[VolatilityIndicator.AVERAGE_TRUE_RANGE]!! // TODO: Better Error Handling

            return firstCandle.isBearish() &&
                secondCandle.isBearish() &&
                firstCandle.close > secondCandle.close &&
                secondCandle.height() > (2 * firstCandleATR)
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }
}
