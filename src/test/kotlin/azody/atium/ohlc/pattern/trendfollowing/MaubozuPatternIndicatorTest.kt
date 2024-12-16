package azody.atium.ohlc.pattern.trendfollowing

import azody.atium.ohlc.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MaubozuPatternIndicatorTest :
    FunSpec({

        test("Positive Test for Bullish Marubozu Patter") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 10.0, 12.0),
                )
            MarubozuPatternIndicator.bullIndicator(data, 0) shouldBe true
        }

        test("Negative Test for Bullish Marubozu Patter") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 9.0, 12.0),
                )
            MarubozuPatternIndicator.bullIndicator(data, 0) shouldBe false
        }

        test("Negative Test for Bullish Marubozu Patter - Index out of Bounds") {
            val data = listOf<OHLC>()
            MarubozuPatternIndicator.bullIndicator(data, 0) shouldBe false
        }

        test("Positive Test for Bearish Marubozu Patter") {
            val data =
                listOf(
                    OHLC(12.0, 12.0, 10.0, 10.0),
                )
            MarubozuPatternIndicator.bearIndicator(data, 0) shouldBe true
        }

        test("Negative Test for Bearish Marubozu Patter") {
            val data =
                listOf(
                    OHLC(12.0, 12.0, 9.0, 10.0),
                )
            MarubozuPatternIndicator.bearIndicator(data, 0) shouldBe false
        }

        test("Negative Test for Bearish Marubozu Patter - Index out of Bounds") {
            val data = listOf<OHLC>()
            MarubozuPatternIndicator.bearIndicator(data, 0) shouldBe false
        }
    })
