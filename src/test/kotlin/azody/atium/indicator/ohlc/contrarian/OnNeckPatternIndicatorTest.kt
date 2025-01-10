package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class OnNeckPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Mirror Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 2.0, 5.0, 1.0, 5.0),
                )
            OnNeckPatternIndicator.bullIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Mirror Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 2.0, 5.0, 1.0, 6.0),
                )
            OnNeckPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Mirror Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 3.0, 5.0),
                    OHLC(1, 2.0, 5.0, 1.0, 5.0),
                )
            OnNeckPatternIndicator.bullIndicator(data, 0) shouldBe false
        }
        test("Positive Test for Mirror Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 12.0, 8.0, 14.0, 10.0),
                )
            OnNeckPatternIndicator.bearIndicator(data, 1) shouldBe true
        }
        test("Negative Test for Mirror Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 8.0, 8.0, 14.0, 10.0),
                )
            OnNeckPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
        test("Negative Test for Mirror Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 12.0, 8.0, 14.0, 10.0),
                )
            OnNeckPatternIndicator.bearIndicator(data, 0) shouldBe false
        }
    })
