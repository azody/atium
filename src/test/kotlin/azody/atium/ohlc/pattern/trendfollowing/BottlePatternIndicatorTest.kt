package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.ohlc.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BottlePatternIndicatorTest :
    FunSpec({
        test("Positive Test for Bottle Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 9.0, 11.0),
                    OHLC(10.0, 13.0, 10.0, 12.0),
                )
            BottlePatternIndicator.bullIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Bottle Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 9.0, 11.0),
                    OHLC(10.0, 13.0, 9.5, 12.0),
                )
            BottlePatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Bottle Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 9.0, 11.0),
                )
            BottlePatternIndicator.bullIndicator(data, 0) shouldBe false
        }
        test("Positive Test for Bottle Bearish Indicator") {
            val data =
                listOf(
                    OHLC(12.0, 13.0, 9.0, 10.0),
                    OHLC(11.0, 11.0, 7.0, 8.0),
                )
            BottlePatternIndicator.bearIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Bottle Bearish Indicator") {
            val data =
                listOf(
                    OHLC(12.0, 13.0, 9.0, 10.0),
                    OHLC(11.0, 11.5, 8.0, 7.0),
                )
            BottlePatternIndicator.bearIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Bottle Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(12.0, 13.0, 9.0, 10.0),
                )
            BottlePatternIndicator.bearIndicator(data, 0) shouldBe false
        }
    })
