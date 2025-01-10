package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HikkakePatternIndicatorTest :
    FunSpec({

        test("Positive Test for Hikkake Bullish Indicator") {

            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 4.0, 10.0),
                    OHLC(1, 8.0, 9.0, 6.0, 4.0),
                    OHLC(1, 7.5, 8.5, 6.5, 7.0),
                    OHLC(1, 8.0, 9.0, 7.0, 8.5),
                    OHLC(1, 8.0, 15.0, 7.5, 18.0),
                )
            HikkakePatternIndicator.bullIndicator(data, 4) shouldBe true
        }

        test("Negative Test for Hikkake Bullish Indicator") {

            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 4.0, 10.0),
                    OHLC(1, 8.0, 9.0, 6.0, 4.0),
                    OHLC(1, 7.5, 9.5, 6.5, 7.0),
                    OHLC(1, 8.0, 9.0, 7.0, 8.5),
                    OHLC(1, 8.0, 15.0, 7.5, 18.0),
                )
            HikkakePatternIndicator.bullIndicator(data, 4) shouldBe false
        }
        test("Negative Test for Hikkake Bullish Indicator - Index Out of Bounds") {

            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 4.0, 10.0),
                    OHLC(1, 8.0, 9.0, 6.0, 4.0),
                    OHLC(1, 7.5, 9.5, 6.5, 7.0),
                    OHLC(1, 8.0, 9.0, 7.0, 8.5),
                )
            HikkakePatternIndicator.bullIndicator(data, 3) shouldBe false
        }

        test("Positive Test for Hikkake Bearish Indicator") {

            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 2.0, 3.0),
                    OHLC(1, 4.5, 9.0, 5.0, 5.5),
                    OHLC(1, 9.0, 6.0, 5.5, 6.0),
                    OHLC(1, 10.0, 10.0, 5.0, 8.0),
                    OHLC(1, 8.0, 8.0, 2.0, 3.0),
                )
            HikkakePatternIndicator.bearIndicator(data, 4) shouldBe true
        }

        test("Negative Test for Hikkake Bearish Indicator") {

            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 2.0, 3.0),
                    OHLC(1, 4.5, 13.0, 5.0, 5.5),
                    OHLC(1, 9.0, 6.0, 5.5, 6.0),
                    OHLC(1, 10.0, 10.0, 5.0, 8.0),
                    OHLC(1, 8.0, 8.0, 2.0, 3.0),
                )
            HikkakePatternIndicator.bearIndicator(data, 4) shouldBe false
        }

        test("Negative Test for Hikkake Bearish Indicator - Index Out of Bounds") {

            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 2.0, 3.0),
                    OHLC(1, 4.5, 13.0, 5.0, 5.5),
                    OHLC(1, 9.0, 6.0, 5.5, 6.0),
                    OHLC(1, 10.0, 10.0, 5.0, 8.0),
                )
            HikkakePatternIndicator.bearIndicator(data, 3) shouldBe false
        }
    })
