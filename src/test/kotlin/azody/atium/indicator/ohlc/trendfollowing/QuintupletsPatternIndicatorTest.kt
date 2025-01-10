package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.OHLC
import azody.atium.domain.OHLCParameter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class QuintupletsPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Bullish Quintuplets Pattern") {
            val data =
                listOf(
                    OHLC(1, 1.0, 2.5, 0.5, 2.0),
                    OHLC(1, 2.0, 3.5, 1.5, 3.0),
                    OHLC(1, 3.0, 4.5, 2.5, 4.0),
                    OHLC(1, 4.0, 5.5, 3.5, 5.0),
                    OHLC(1, 5.0, 6.5, 4.5, 6.0),
                )

            QuintupletsPatternIndicator.bullIndicator(
                data,
                4,
                mapOf(OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0),
            ) shouldBe true
        }

        test("Negative Test for Bullish Quintuplets Pattern") {
            val data =
                listOf(
                    OHLC(1, 1.0, 2.5, 0.5, 2.0),
                    OHLC(1, 2.0, 3.5, 1.5, 3.0),
                    OHLC(1, 3.0, 4.5, 2.5, 4.0),
                    OHLC(1, 4.0, 5.5, 3.5, 5.0),
                    OHLC(1, 5.0, 6.5, 4.5, 6.0),
                )

            QuintupletsPatternIndicator.bullIndicator(
                data,
                4,
                mapOf(OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0),
            ) shouldBe false
        }
        test("Negative Test for Bullish Quintuplets Pattern - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 1.0, 2.5, 0.5, 2.0),
                    OHLC(1, 2.0, 3.5, 1.5, 3.0),
                    OHLC(1, 3.0, 4.5, 2.5, 4.0),
                    OHLC(1, 4.0, 5.5, 3.5, 5.0),
                    OHLC(1, 5.0, 6.5, 4.5, 6.0),
                )

            QuintupletsPatternIndicator.bullIndicator(
                data,
                3,
                mapOf(OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0),
            ) shouldBe false
        }
        test("Positive Test for Bearish Quintuplets Pattern") {
            val data =
                listOf(
                    OHLC(1, 6.0, 6.5, 4.5, 5.0),
                    OHLC(1, 5.0, 5.5, 3.5, 4.0),
                    OHLC(1, 4.0, 4.5, 2.5, 3.0),
                    OHLC(1, 3.0, 3.5, 1.5, 2.0),
                    OHLC(1, 2.0, 2.5, 0.5, 1.0),
                )

            QuintupletsPatternIndicator.bearIndicator(
                data,
                4,
                mapOf(OHLCParameter.MAX_BODY_HEIGHT.toString() to 2.0),
            ) shouldBe true
        }

        test("Negative Test for Bearish Quintuplets Pattern") {
            val data =
                listOf(
                    OHLC(1, 6.0, 6.5, 4.5, 5.0),
                    OHLC(1, 5.0, 5.5, 3.5, 4.0),
                    OHLC(1, 4.0, 4.5, 2.5, 3.0),
                    OHLC(1, 3.0, 3.5, 1.5, 2.0),
                    OHLC(1, 2.0, 2.5, 0.5, 1.0),
                )

            QuintupletsPatternIndicator.bearIndicator(
                data,
                4,
                mapOf(OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0),
            ) shouldBe false
        }
        test("Negative Test for Bearish Quintuplets Pattern - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 6.0, 6.5, 4.5, 5.0),
                    OHLC(1, 5.0, 5.5, 3.5, 4.0),
                    OHLC(1, 4.0, 4.5, 2.5, 3.0),
                    OHLC(1, 3.0, 3.5, 1.5, 2.0),
                    OHLC(1, 2.0, 2.5, 0.5, 1.0),
                )

            QuintupletsPatternIndicator.bearIndicator(
                data,
                3,
                mapOf(OHLCParameter.MAX_BODY_HEIGHT.toString() to 1.0),
            ) shouldBe false
        }
    })
