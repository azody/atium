package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import azody.atium.domain.OHLCParameter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HammerPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Hammer Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 7.0, 8.0),
                    OHLC(1, 7.0, 8.0, 4.0, 8.0),
                    OHLC(1, 8.0, 15.0, 6.0, 12.0),
                )
            HammerPatternIndicator.bullIndicator(data, 2, options) shouldBe true
        }
        test("Negative Test for Hammer Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 7.0, 8.0),
                    OHLC(1, 7.0, 8.5, 4.0, 8.0),
                    OHLC(1, 8.0, 15.0, 6.0, 12.0),
                )
            HammerPatternIndicator.bullIndicator(data, 2, options) shouldBe false
        }
        test("Negative Test for Hammer Bullish Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 7.0, 8.0),
                    OHLC(1, 7.0, 8.0, 4.0, 8.0),
                    OHLC(1, 8.0, 15.0, 6.0, 12.0),
                )
            HammerPatternIndicator.bullIndicator(data, 1, options) shouldBe false
        }
        test("Positive Test for Hammer Bearish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 8.0, 12.0, 7.0, 10.0),
                    OHLC(1, 11.0, 15.0, 10.0, 10.0),
                    OHLC(1, 12.0, 15.0, 6.0, 8.0),
                )
            HammerPatternIndicator.bearIndicator(data, 2, options) shouldBe true
        }
        test("Negative Test for Hammer Bearish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 8.0, 12.0, 7.0, 10.0),
                    OHLC(1, 11.0, 15.0, 8.0, 8.0),
                    OHLC(1, 12.0, 15.0, 6.0, 8.0),
                )
            HammerPatternIndicator.bearIndicator(data, 2, options) shouldBe false
        }
        test("Negative Test for Hammer Bearish Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 8.0, 12.0, 7.0, 10.0),
                    OHLC(1, 11.0, 15.0, 10.0, 10.0),
                    OHLC(1, 12.0, 15.0, 6.0, 8.0),
                )
            HammerPatternIndicator.bearIndicator(data, 1, options) shouldBe false
        }
    })
