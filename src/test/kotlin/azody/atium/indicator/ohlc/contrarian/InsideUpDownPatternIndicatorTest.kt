package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import azody.atium.domain.OHLCParameter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class InsideUpDownPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Inside Up Down Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 6.0, 9.0, 5.0, 7.0),
                    OHLC(1, 6.0, 15.0, 5.0, 14.0),
                )
            InsideUpDownPatternIndicator.bullIndicator(data, 2, options) shouldBe true
        }
        test("Negative Test for Inside Up Down Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 7.0, 9.0, 5.0, 6.0),
                    OHLC(1, 6.0, 15.0, 5.0, 14.0),
                )
            InsideUpDownPatternIndicator.bullIndicator(data, 2, options) shouldBe false
        }
        test("Negative Test for Inside Up Down Bullish Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 6.0, 9.0, 5.0, 7.0),
                    OHLC(1, 6.0, 15.0, 5.0, 14.0),
                )
            InsideUpDownPatternIndicator.bullIndicator(data, 1, options) shouldBe false
        }
        test("Positive Test for Inside Up Down Bearish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 7.0, 9.0, 5.0, 6.0),
                    OHLC(1, 7.0, 15.0, 5.0, 4.0),
                )
            InsideUpDownPatternIndicator.bearIndicator(data, 2, options) shouldBe true
        }
        test("Negative Test for Inside Up Down Bearish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 7.0, 9.0, 5.0, 6.0),
                    OHLC(1, 7.0, 15.0, 5.0, 6.0),
                )
            InsideUpDownPatternIndicator.bearIndicator(data, 2, options) shouldBe false
        }
        test("Negative Test for Inside Up Down Bearish Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0,
                )
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 7.0, 9.0, 5.0, 6.0),
                    OHLC(1, 7.0, 15.0, 5.0, 4.0),
                )
            InsideUpDownPatternIndicator.bearIndicator(data, 1, options) shouldBe false
        }
    })
