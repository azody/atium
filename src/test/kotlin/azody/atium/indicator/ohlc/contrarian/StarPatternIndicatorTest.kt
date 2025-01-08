package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StarPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Star Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(2.0, 7.0, 2.0, 4.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            StarPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Star Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 7.0, 2.0, 4.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            StarPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Star Pattern Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(2.0, 7.0, 2.0, 4.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            StarPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Star Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(12.0, 16.0, 10.0, 14.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            StarPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Star Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(9.0, 16.0, 10.0, 14.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            StarPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Positive Test for Star Pattern Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(12.0, 16.0, 10.0, 14.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            StarPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
