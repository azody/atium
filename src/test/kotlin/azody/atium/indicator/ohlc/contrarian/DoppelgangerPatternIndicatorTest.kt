package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DoppelgangerPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Dopplelganger Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 15.0, 17.0, 9.0, 10.0),
                    OHLC(1, 10.0, 11.0, 8.0, 9.0),
                    OHLC(1, 8.0, 11.0, 8.0, 9.0),
                )
            DoppelgangerPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Dopplelganger Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 15.0, 17.0, 9.0, 10.0),
                    OHLC(1, 10.0, 11.0, 8.0, 9.0),
                    OHLC(1, 8.0, 11.0, 7.0, 9.0),
                )
            DoppelgangerPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Dopplelganger Bullish Indicator - Index out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 15.0, 17.0, 9.0, 10.0),
                    OHLC(1, 10.0, 11.0, 8.0, 9.0),
                    OHLC(1, 8.0, 11.0, 8.0, 9.0),
                )
            DoppelgangerPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Dopplelganger Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 10.0, 16.0, 8.0, 9.0),
                    OHLC(1, 8.0, 16.0, 8.0, 9.0),
                )
            DoppelgangerPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Dopplelganger Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 15.0, 17.0, 9.0, 10.0),
                    OHLC(1, 10.0, 11.0, 8.0, 9.0),
                    OHLC(1, 8.0, 11.0, 7.0, 9.0),
                )
            DoppelgangerPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Dopplelganger Bearish Indicator - Index out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 5.0, 12.0, 3.0, 10.0),
                    OHLC(1, 10.0, 16.0, 8.0, 9.0),
                    OHLC(1, 8.0, 16.0, 8.0, 9.0),
                )
            DoppelgangerPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
