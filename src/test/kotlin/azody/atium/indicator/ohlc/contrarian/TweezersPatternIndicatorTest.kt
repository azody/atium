package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TweezersPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Tweezer Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 7.0, 10.0, 2.0, 5.0),
                    OHLC(1, 5.0, 10.0, 2.0, 7.0),
                )
            TweezersPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Tweezer Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 7.0, 10.0, 1.0, 5.0),
                    OHLC(1, 5.0, 10.0, 2.0, 7.0),
                )
            TweezersPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Tweezer Pattern Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 7.0, 10.0, 2.0, 5.0),
                    OHLC(1, 5.0, 10.0, 2.0, 7.0),
                )
            TweezersPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Tweezer Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 7.0, 15.0, 6.0, 12.0),
                    OHLC(1, 12.0, 15.0, 6.0, 7.0),
                )
            TweezersPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Tweezer Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 12.0, 15.0, 6.0, 7.0),
                    OHLC(1, 12.0, 15.0, 6.0, 7.0),
                )
            TweezersPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Tweezer Pattern Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 7.0, 15.0, 6.0, 12.0),
                    OHLC(1, 12.0, 15.0, 6.0, 7.0),
                )
            TweezersPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
