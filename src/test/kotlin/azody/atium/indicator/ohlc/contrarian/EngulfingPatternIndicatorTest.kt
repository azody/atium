package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EngulfingPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Engulfing Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 7.0, 8.0),
                    OHLC(7.0, 15.0, 6.0, 12.0),
                )
            EngulfingPatternIndicator.bullIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Engulfing Bullish Indicator") {
            val data =
                listOf(
                    OHLC(12.0, 12.0, 7.0, 8.0),
                    OHLC(7.0, 15.0, 6.0, 12.0),
                )
            EngulfingPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Engulfing Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 7.0, 8.0),
                    OHLC(7.0, 15.0, 6.0, 12.0),
                )
            EngulfingPatternIndicator.bullIndicator(data, 0) shouldBe false
        }
        test("Positive Test for Engulfing Bearish Indicator") {
            val data =
                listOf(
                    OHLC(8.0, 12.0, 7.0, 10.0),
                    OHLC(12.0, 15.0, 6.0, 7.0),
                )
            EngulfingPatternIndicator.bearIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Engulfing Bearish Indicator") {
            val data =
                listOf(
                    OHLC(12.0, 12.0, 7.0, 8.0),
                    OHLC(7.0, 15.0, 6.0, 12.0),
                )
            EngulfingPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Engulfing Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(8.0, 12.0, 7.0, 10.0),
                    OHLC(12.0, 15.0, 6.0, 7.0),
                )
            EngulfingPatternIndicator.bearIndicator(data, 0) shouldBe false
        }
    })
