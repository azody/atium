package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HPatternIndicatorTest :
    FunSpec({
        test("Positive Test for H Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 9.0, 11.0),
                    OHLC(1, 11.0, 13.0, 10.0, 11.0),
                    OHLC(1, 12.0, 16.0, 11.0, 15.0),
                )
            HPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for H Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 9.0, 11.0),
                    OHLC(1, 11.0, 13.0, 10.0, 11.0),
                    OHLC(1, 12.0, 16.0, 9.0, 15.0),
                )
            HPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for H Pattern Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 9.0, 11.0),
                    OHLC(1, 10.0, 13.0, 9.5, 12.0),
                )
            HPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for H Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 11.0, 8.0, 9.0),
                    OHLC(1, 9.0, 10.0, 8.0, 9.0),
                    OHLC(1, 7.0, 7.5, 4.0, 5.0),
                )
            HPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for H Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 11.0, 8.0, 9.0),
                    OHLC(1, 9.0, 10.0, 8.0, 8.0),
                    OHLC(1, 7.0, 7.5, 4.0, 5.0),
                )
            HPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for H Pattern Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 9.0, 11.0),
                    OHLC(1, 10.0, 13.0, 9.5, 12.0),
                )
            HPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
