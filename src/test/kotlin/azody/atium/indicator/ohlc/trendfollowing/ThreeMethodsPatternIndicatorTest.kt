package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ThreeMethodsPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Three Methods Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(8.0, 9.0, 7.0, 7.0),
                    OHLC(7.0, 8.0, 6.0, 6.0),
                    OHLC(6.0, 7.0, 5.0, 5.0),
                    OHLC(7.0, 6.0, 6.0, 15.0),
                )
            ThreeMethodsPatternIndicator.bullIndicator(data, 4) shouldBe true
        }
        test("Negative Test for Three Methods Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(8.0, 9.0, 7.0, 7.0),
                    OHLC(7.0, 8.0, 6.0, 8.0),
                    OHLC(6.0, 7.0, 5.0, 5.0),
                    OHLC(7.0, 6.0, 6.0, 15.0),
                )
            ThreeMethodsPatternIndicator.bullIndicator(data, 4) shouldBe false
        }
        test("Negative Test for Three Methods Pattern Bullish Indicator - Index out of bounds") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(8.0, 9.0, 7.0, 7.0),
                    OHLC(7.0, 8.0, 6.0, 6.0),
                    OHLC(6.0, 7.0, 5.0, 5.0),
                    OHLC(7.0, 6.0, 6.0, 15.0),
                )
            ThreeMethodsPatternIndicator.bullIndicator(data, 3) shouldBe false
        }
        test("Positive Test for Three Methods Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(7.0, 9.0, 7.0, 8.0),
                    OHLC(6.0, 8.0, 6.0, 7.0),
                    OHLC(5.0, 7.0, 5.0, 6.0),
                    OHLC(7.0, 7.0, 1.0, 1.0),
                )
            ThreeMethodsPatternIndicator.bearIndicator(data, 4) shouldBe true
        }
        test("Negative Test for Three Methods Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(7.0, 9.0, 7.0, 8.0),
                    OHLC(6.0, 8.0, 6.0, 7.0),
                    OHLC(5.0, 15.0, 5.0, 6.0),
                    OHLC(7.0, 7.0, 1.0, 1.0),
                )
            ThreeMethodsPatternIndicator.bearIndicator(data, 4) shouldBe false
        }
        test("Negative Test for Three Methods Pattern Bearish Indicator - Index out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(7.0, 9.0, 7.0, 8.0),
                    OHLC(6.0, 8.0, 6.0, 7.0),
                    OHLC(5.0, 7.0, 5.0, 6.0),
                    OHLC(7.0, 7.0, 1.0, 1.0),
                )
            ThreeMethodsPatternIndicator.bearIndicator(data, 4) shouldBe true
        }
    })
