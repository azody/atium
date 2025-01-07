package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MirrorPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Mirror Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(8.0, 13.0, 7.0, 10.0),
                    OHLC(10.0, 13.0, 7.0, 8.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            MirrorPatternIndicator.bullIndicator(data, 3) shouldBe true
        }
        test("Negative Test for Mirror Bullish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(8.0, 13.0, 7.0, 10.0),
                    OHLC(10.0, 14.0, 7.0, 8.0),
                    OHLC(5.0, 12.0, 3.0, 9.0),
                )
            MirrorPatternIndicator.bullIndicator(data, 3) shouldBe false
        }
        test("Negative Test for Mirror Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(8.0, 13.0, 7.0, 10.0),
                    OHLC(10.0, 13.0, 7.0, 8.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            MirrorPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Positive Test for Mirror Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(8.0, 13.0, 7.0, 10.0),
                    OHLC(10.0, 13.0, 7.0, 8.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            MirrorPatternIndicator.bearIndicator(data, 3) shouldBe true
        }
        test("Negative Test for Mirror Bearish Indicator") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(8.0, 13.0, 7.0, 10.0),
                    OHLC(10.0, 15.0, 7.0, 8.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            MirrorPatternIndicator.bearIndicator(data, 3) shouldBe false
        }
        test("Negative Test for Mirror Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(8.0, 13.0, 7.0, 10.0),
                    OHLC(10.0, 13.0, 7.0, 8.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            MirrorPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
    })
