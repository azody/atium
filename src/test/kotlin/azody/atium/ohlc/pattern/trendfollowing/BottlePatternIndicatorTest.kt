package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.ohlc.OHLC
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BottlePatternIndicatorTest {

    @Test
    fun bull_indicator_positive() {
        val data = listOf(
            OHLC(10.0, 12.0, 10.0, 11.0),
            OHLC(11.0, 13.0, 11.0, 12.0)
        )
        assert(BottlePatternIndicator.bull_indicator(data, 1))
    }

    @Test
    fun bear_indicator() {
    }
}