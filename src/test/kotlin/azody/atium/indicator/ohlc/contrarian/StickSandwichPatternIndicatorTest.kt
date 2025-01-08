package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StickSandwichPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Stick Sandwich Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 7.0, 2.0, 9.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            StickSandwichPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Stick Sandwich Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(4.0, 7.0, 2.0, 9.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            StickSandwichPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Stick Sandwich Pattern Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 7.0, 2.0, 9.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            StickSandwichPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Stick Sandwich Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(9.0, 7.0, 4.0, 6.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            StickSandwichPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Stick Sandwich Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(4.0, 7.0, 2.0, 9.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            StickSandwichPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Stick Sandwich Pattern Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(6.0, 7.0, 2.0, 9.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            StickSandwichPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
