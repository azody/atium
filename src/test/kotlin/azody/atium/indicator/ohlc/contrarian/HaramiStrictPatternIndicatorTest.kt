package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HaramiStrictPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Harami Strict Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 6.0, 9.5, 5.5, 9.0),
                )
            HaramiStrictPatternIndicator.bullIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Harami Strict Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 6.0, 15.0, 4.0, 9.0),
                )
            HaramiStrictPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Harami Strict Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 6.0, 9.5, 5.5, 9.0),
                )
            HaramiStrictPatternIndicator.bullIndicator(data, 0) shouldBe false
        }
        test("Positive Test for Harami Strict Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 9.0, 9.5, 5.5, 6.0),
                )
            HaramiStrictPatternIndicator.bearIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Harami Strict Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 9.0, 15.0, 4.0, 6.0),
                )
            HaramiStrictPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Harami Strict Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 9.0, 9.5, 5.5, 6.0),
                )
            HaramiStrictPatternIndicator.bearIndicator(data, 0) shouldBe false
        }
    })
