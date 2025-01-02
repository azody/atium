package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BarrierPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Barrier Bullish Indicator") {
            val data =
                listOf(
                    OHLC(12.0, 12.0, 9.0, 10.0),
                    OHLC(12.0, 13.0, 9.0, 10.0),
                    OHLC(10.0, 12.0, 9.0, 12.0),
                )
            BarrierPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Barrier Bullish Indicator") {
            val data =
                listOf(
                    OHLC(12.0, 12.0, 9.0, 10.0),
                    OHLC(12.0, 13.0, 8.0, 10.0),
                    OHLC(10.0, 12.0, 9.0, 12.0),
                )
            BarrierPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Positive Test for Barrier Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(12.0, 12.0, 9.0, 10.0),
                    OHLC(12.0, 13.0, 9.0, 10.0),
                    OHLC(10.0, 12.0, 9.0, 12.0),
                )
            BarrierPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Barrier Bearish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 9.0, 12.0),
                    OHLC(10.0, 12.0, 9.0, 12.0),
                    OHLC(12.0, 12.0, 9.0, 10.0),
                )
            BarrierPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Barrier Bearish Indicator") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 9.0, 12.0),
                    OHLC(10.0, 13.0, 9.0, 12.0),
                    OHLC(12.0, 12.0, 9.0, 10.0),
                )
            BarrierPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Barrier Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(10.0, 12.0, 9.0, 12.0),
                    OHLC(10.0, 12.0, 9.0, 12.0),
                    OHLC(12.0, 12.0, 9.0, 10.0),
                )
            BarrierPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
