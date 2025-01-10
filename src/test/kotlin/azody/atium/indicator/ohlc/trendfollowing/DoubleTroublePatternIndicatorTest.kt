package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.OHLC
import azody.atium.domain.VolatilityIndicator
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DoubleTroublePatternIndicatorTest :
    FunSpec({
        test("Positive Test for Double Trouble Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 14.0, 9.0, 13.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 1.0)),
                    OHLC(1, 11.0, 18.0, 10.0, 16.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 1.0)),
                )
            DoubleTroublePatternIndicator.bullIndicator(data, 1) shouldBe true
        }

        test("Negative Test for Double Trouble Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 14.0, 9.0, 13.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 10.0)),
                    OHLC(1, 11.0, 18.0, 10.0, 16.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 10.0)),
                )
            DoubleTroublePatternIndicator.bullIndicator(data, 1) shouldBe false
        }

        test("Negative Test for Double Trouble Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 11.0, 18.0, 10.0, 16.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 10.0)),
                )
            DoubleTroublePatternIndicator.bullIndicator(data, 1) shouldBe false
        }

        test("Positive Test for Double Trouble Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 11.0, 7.0, 8.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 1.0)),
                    OHLC(1, 9.0, 10.0, 4.0, 5.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 1.0)),
                )
            DoubleTroublePatternIndicator.bearIndicator(data, 1) shouldBe true
        }

        test("Negative Test for Double Trouble Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 11.0, 7.0, 8.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 10.0)),
                    OHLC(1, 9.0, 10.0, 4.0, 5.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 10.0)),
                )
            DoubleTroublePatternIndicator.bearIndicator(data, 1) shouldBe false
        }

        test("Negative Test for Double Trouble Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 11.0, 18.0, 10.0, 16.0, mapOf(VolatilityIndicator.AVERAGE_TRUE_RANGE to 10.0)),
                )
            DoubleTroublePatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
