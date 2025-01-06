package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EuphoriaPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Euphoria Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 10.0, 2.0, 8.0),
                    OHLC(9.0, 10.0, 2.0, 6.0),
                    OHLC(8.0, 10.0, 2.0, 4.0),
                )
            EuphoriaPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Euphoria Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 10.0, 2.0, 8.0),
                    OHLC(9.0, 10.0, 2.0, 6.0),
                    OHLC(8.0, 10.0, 2.0, 7.0),
                )
            EuphoriaPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Euphoria Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 10.0, 2.0, 8.0),
                    OHLC(9.0, 10.0, 2.0, 6.0),
                    OHLC(8.0, 10.0, 2.0, 4.0),
                )
            EuphoriaPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Euphoria Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 10.0, 2.0, 6.0),
                    OHLC(6.0, 10.0, 2.0, 8.0),
                    OHLC(7.0, 10.0, 2.0, 10.0),
                )
            EuphoriaPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Euphoria Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 10.0, 2.0, 6.0),
                    OHLC(8.0, 10.0, 2.0, 6.0),
                    OHLC(7.0, 10.0, 2.0, 10.0),
                )
            EuphoriaPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Euphoria Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(5.0, 10.0, 2.0, 6.0),
                    OHLC(6.0, 10.0, 2.0, 8.0),
                    OHLC(7.0, 10.0, 2.0, 10.0),
                )
            EuphoriaPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
