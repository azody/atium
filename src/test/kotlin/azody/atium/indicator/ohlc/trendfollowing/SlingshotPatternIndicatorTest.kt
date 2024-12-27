package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SlingshotPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Slingshot Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(8.0, 11.0, 7.0, 10.0),
                    OHLC(12.0, 16.0, 11.0, 15.0),
                    OHLC(15.0, 16.0, 11.0, 12.0),
                    OHLC(12.0, 18.0, 11.0, 17.0),
                )
            SlingshotPatternIndicator.bullIndicator(data, 3) shouldBe true
        }
        test("Negative Test for Slingshot Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(8.0, 11.0, 7.0, 10.0),
                    OHLC(15.0, 16.0, 11.0, 12.0),
                    OHLC(15.0, 16.0, 11.0, 12.0),
                    OHLC(12.0, 18.0, 11.0, 17.0),
                )
            SlingshotPatternIndicator.bullIndicator(data, 3) shouldBe false
        }
        test("Negative Test for Slingshot Pattern Bullish Indicator Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(8.0, 11.0, 7.0, 10.0),
                    OHLC(15.0, 16.0, 11.0, 12.0),
                    OHLC(12.0, 18.0, 11.0, 17.0),
                )
            SlingshotPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Positive Test for Slingshot Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(15.0, 16.0, 10.0, 11.0),
                    OHLC(9.0, 10.0, 6.0, 7.0),
                    OHLC(7.0, 10.0, 6.0, 9.0),
                    OHLC(9.0, 10.0, 4.0, 5.0),
                )
            SlingshotPatternIndicator.bearIndicator(data, 3) shouldBe true
        }
        test("Negative Test for Slingshot Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(15.0, 16.0, 10.0, 11.0),
                    OHLC(7.0, 10.0, 6.0, 9.0),
                    OHLC(7.0, 10.0, 6.0, 9.0),
                    OHLC(9.0, 10.0, 4.0, 5.0),
                )
            SlingshotPatternIndicator.bearIndicator(data, 3) shouldBe false
        }
        test("Negative Test for Slingshot Pattern Bearish Indicator Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(15.0, 16.0, 10.0, 11.0),
                    OHLC(7.0, 10.0, 6.0, 9.0),
                    OHLC(7.0, 10.0, 6.0, 9.0),
                    OHLC(9.0, 10.0, 4.0, 5.0),
                )
            SlingshotPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
    })
