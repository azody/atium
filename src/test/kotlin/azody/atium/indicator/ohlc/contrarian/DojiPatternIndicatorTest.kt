package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DojiPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Doji Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(5.0, 7.0, 2.0, 5.0),
                    OHLC(6.0, 10.0, 5.0, 8.0),
                )
            DojiPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Doji Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(5.0, 7.0, 2.0, 5.0),
                    OHLC(2.0, 10.0, 5.0, 4.0),
                )
            DojiPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Doji Pattern Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(5.0, 7.0, 2.0, 5.0),
                    OHLC(6.0, 10.0, 5.0, 8.0),
                )
            DojiPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Doji Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(10.0, 12.0, 7.0, 10.0),
                    OHLC(10.0, 11.0, 5.0, 6.0),
                )
            DojiPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Doji Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(11.0, 12.0, 7.0, 10.0),
                    OHLC(10.0, 11.0, 5.0, 6.0),
                )
            DojiPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Positive Test for Doji Pattern Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(10.0, 12.0, 7.0, 10.0),
                    OHLC(10.0, 11.0, 5.0, 6.0),
                )
            DojiPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
