package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import azody.atium.domain.OHLCParameter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SpinningTopPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Spinning Top Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(10.0, 12.0, 4.0, 5.0),
                    OHLC(7.0, 12.0, 4.0, 7.5),
                    OHLC(5.0, 12.0, 4.0, 10.0),
                )
            SpinningTopPatternIndicator.bullIndicator(data, 2, options) shouldBe true
        }
        test("Negative Test for Spinning Top Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(10.0, 12.0, 4.0, 5.0),
                    OHLC(7.0, 12.0, 4.0, 8.5),
                    OHLC(5.0, 12.0, 4.0, 10.0),
                )
            SpinningTopPatternIndicator.bullIndicator(data, 2, options) shouldBe false
        }
        test("Negative Test for Spinning Top Bullish Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(10.0, 12.0, 4.0, 5.0),
                    OHLC(7.0, 12.0, 4.0, 7.5),
                    OHLC(5.0, 12.0, 4.0, 10.0),
                )
            SpinningTopPatternIndicator.bullIndicator(data, 1, options) shouldBe false
        }
        test("Positive Test for Spinning Top Bear Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(5.0, 12.0, 4.0, 10.0),
                    OHLC(7.5, 12.0, 4.0, 7.0),
                    OHLC(10.0, 12.0, 4.0, 5.0),
                )
            SpinningTopPatternIndicator.bearIndicator(data, 2, options) shouldBe true
        }
        test("Negative Test for Spinning Top Bear Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(5.0, 12.0, 4.0, 10.0),
                    OHLC(8.5, 12.0, 4.0, 7.0),
                    OHLC(10.0, 12.0, 4.0, 5.0),
                )
            SpinningTopPatternIndicator.bearIndicator(data, 2, options) shouldBe false
        }
        test("Negative Test for Spinning Top Bear Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0,
                    OHLCParameter.WICK_SIZE.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(5.0, 12.0, 4.0, 10.0),
                    OHLC(7.5, 12.0, 4.0, 7.0),
                    OHLC(10.0, 12.0, 4.0, 5.0),
                )
            SpinningTopPatternIndicator.bearIndicator(data, 1, options) shouldBe false
        }
    })
