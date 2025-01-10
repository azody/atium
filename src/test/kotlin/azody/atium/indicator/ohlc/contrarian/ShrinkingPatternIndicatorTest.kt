package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ShrinkingPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Shrinking Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 20.0, 22.0, 13.0, 15.0),
                    OHLC(1, 15.0, 15.0, 5.0, 11.0),
                    OHLC(1, 14.0, 15.0, 5.0, 11.0),
                    OHLC(1, 13.0, 15.0, 5.0, 11.0),
                    OHLC(1, 10.0, 17.0, 8.0, 17.0),
                )
            ShrinkingPatternIndicator.bullIndicator(data, 4) shouldBe true
        }
        test("Negative Test for Shrinking Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 20.0, 22.0, 13.0, 15.0),
                    OHLC(1, 15.0, 15.0, 5.0, 11.0),
                    OHLC(1, 14.0, 15.0, 5.0, 11.0),
                    OHLC(1, 13.0, 15.0, 5.0, 11.0),
                    OHLC(1, 10.0, 17.0, 8.0, 14.0),
                )
            ShrinkingPatternIndicator.bullIndicator(data, 4) shouldBe false
        }
        test("Negative Test for Shrinking Pattern Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 20.0, 22.0, 13.0, 15.0),
                    OHLC(1, 15.0, 15.0, 5.0, 11.0),
                    OHLC(1, 14.0, 15.0, 5.0, 11.0),
                    OHLC(1, 13.0, 15.0, 5.0, 11.0),
                    OHLC(1, 10.0, 17.0, 8.0, 17.0),
                )
            ShrinkingPatternIndicator.bullIndicator(data, 3) shouldBe false
        }
        test("Positive Test for Shrinking Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 15.0, 30.0, 13.0, 25.0),
                    OHLC(1, 25.0, 30.0, 24.0, 30.0),
                    OHLC(1, 26.0, 30.0, 24.0, 30.0),
                    OHLC(1, 27.0, 30.0, 24.0, 30.0),
                    OHLC(1, 28.0, 30.0, 15.0, 17.0),
                )
            ShrinkingPatternIndicator.bearIndicator(data, 4) shouldBe true
        }
        test("Negative Test for Shrinking Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 15.0, 30.0, 13.0, 25.0),
                    OHLC(1, 25.0, 30.0, 24.0, 30.0),
                    OHLC(1, 26.0, 30.0, 24.0, 36.0),
                    OHLC(1, 27.0, 30.0, 24.0, 30.0),
                    OHLC(1, 28.0, 30.0, 15.0, 17.0),
                )
            ShrinkingPatternIndicator.bearIndicator(data, 4) shouldBe false
        }
        test("Negative Test for Shrinking Pattern Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 15.0, 30.0, 13.0, 25.0),
                    OHLC(1, 25.0, 30.0, 24.0, 30.0),
                    OHLC(1, 26.0, 30.0, 24.0, 30.0),
                    OHLC(1, 27.0, 30.0, 24.0, 30.0),
                    OHLC(1, 28.0, 30.0, 15.0, 17.0),
                )
            ShrinkingPatternIndicator.bearIndicator(data, 3) shouldBe false
        }
    })
