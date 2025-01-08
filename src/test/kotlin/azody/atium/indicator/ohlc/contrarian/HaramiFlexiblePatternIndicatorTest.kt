package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HaramiFlexiblePatternIndicatorTest :
    FunSpec({
        test("Positive Test for Harami Flexible Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 15.0, 4.0, 9.0),
                )
            HaramiFlexiblePatternIndicator.bullIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Harami Flexible Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(9.0, 15.0, 4.0, 6.0),
                )
            HaramiFlexiblePatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Harami Flexible Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 15.0, 4.0, 9.0),
                )
            HaramiFlexiblePatternIndicator.bullIndicator(data, 0) shouldBe false
        }
        test("Positive Test for Harami Flexible Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(9.0, 15.0, 4.0, 6.0),
                )
            HaramiFlexiblePatternIndicator.bearIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Harami Flexible Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(9.0, 15.0, 4.0, 4.0),
                )
            HaramiFlexiblePatternIndicator.bearIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Harami Flexible Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(9.0, 15.0, 4.0, 6.0),
                )
            HaramiFlexiblePatternIndicator.bearIndicator(data, 0) shouldBe false
        }
    })
