package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TasukiPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Tasuki Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(8.0, 11.0, 6.0, 10.0),
                    OHLC(12.0, 15.0, 13.0, 14.0),
                    OHLC(13.0, 16.0, 11.0, 11.5),
                )
            TasukiPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Tasuki Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(8.0, 11.0, 6.0, 10.0),
                    OHLC(12.0, 15.0, 13.0, 14.0),
                    OHLC(13.0, 16.0, 11.0, 10.0),
                )
            TasukiPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Tasuki Pattern Bullish Indicator Index out of Bounds Exception") {
            val data =
                listOf(
                    OHLC(8.0, 11.0, 6.0, 10.0),
                    OHLC(12.0, 15.0, 13.0, 14.0),
                    OHLC(13.0, 16.0, 11.0, 11.0),
                )
            TasukiPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Tasuki Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(14.0, 15.0, 9.0, 10.0),
                    OHLC(8.0, 9.0, 5.0, 6.0),
                    OHLC(6.0, 9.5, 5.0, 8.5),
                )
            TasukiPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Tasuki Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(14.0, 15.0, 9.0, 10.0),
                    OHLC(8.0, 9.0, 5.0, 6.0),
                    OHLC(6.0, 9.5, 5.0, 8.0),
                )
            TasukiPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Tasuki Pattern Bearish Indicator Index Out of Bounds Exception") {
            val data =
                listOf(
                    OHLC(14.0, 15.0, 9.0, 10.0),
                    OHLC(8.0, 9.0, 5.0, 6.0),
                    OHLC(6.0, 9.5, 5.0, 8.0),
                )
            TasukiPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
