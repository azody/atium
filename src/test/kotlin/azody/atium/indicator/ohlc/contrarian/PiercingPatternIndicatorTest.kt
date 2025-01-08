package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PiercingPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Piercing Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 8.0, 9.0),
                    OHLC(9.0, 10.0, 4.0, 5.0),
                    OHLC(3.0, 2.0, 8.0, 7.0),
                )
            PiercingPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Piercing Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 8.0, 9.0),
                    OHLC(9.0, 10.0, 4.0, 5.0),
                    OHLC(3.0, 2.0, 8.0, 5.0),
                )
            PiercingPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Piercing Pattern Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 8.0, 9.0),
                    OHLC(9.0, 10.0, 4.0, 5.0),
                    OHLC(3.0, 2.0, 8.0, 7.0),
                )
            PiercingPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Piercing Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 4.0, 8.0),
                    OHLC(5.0, 12.0, 6.0, 10.0),
                    OHLC(12.0, 14.0, 6.0, 7.0),
                )
            PiercingPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Piercing Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(8.0, 12.0, 4.0, 5.0),
                    OHLC(5.0, 12.0, 6.0, 10.0),
                    OHLC(12.0, 14.0, 6.0, 7.0),
                )
            PiercingPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Piercing Pattern Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 4.0, 8.0),
                    OHLC(5.0, 12.0, 6.0, 10.0),
                    OHLC(12.0, 14.0, 6.0, 7.0),
                )
            PiercingPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
