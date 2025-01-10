package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AbandonedBabyPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Abandoned Baby Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 12.0, 12.0, 9.0, 10.0),
                    OHLC(1, 8.0, 8.2, 8.0, 8.0),
                    OHLC(1, 10.0, 12.0, 9.0, 12.0),
                )
            AbandonedBabyPatternIndicator.bullIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Abandoned Baby Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 12.0, 12.0, 9.0, 10.0),
                    OHLC(1, 8.0, 9.0, 8.0, 8.0),
                    OHLC(1, 10.0, 12.0, 9.0, 12.0),
                )
            AbandonedBabyPatternIndicator.bullIndicator(data, 2) shouldBe false
        }
        test("Positive Test for Abandoned Baby Bullish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 12.0, 12.0, 9.0, 10.0),
                    OHLC(1, 8.0, 8.2, 8.0, 8.0),
                    OHLC(1, 10.0, 12.0, 9.0, 12.0),
                )
            AbandonedBabyPatternIndicator.bullIndicator(data, 1) shouldBe false
        }
        test("Positive Test for Abandoned Baby Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 9.0, 12.0),
                    OHLC(1, 15.0, 20.0, 14.0, 15.0),
                    OHLC(1, 12.0, 12.0, 9.0, 10.0),
                )
            AbandonedBabyPatternIndicator.bearIndicator(data, 2) shouldBe true
        }
        test("Negative Test for Abandoned Baby Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 9.0, 12.0),
                    OHLC(1, 15.0, 20.0, 14.0, 15.0),
                    OHLC(1, 10.0, 12.0, 9.0, 12.0),
                )
            AbandonedBabyPatternIndicator.bearIndicator(data, 2) shouldBe false
        }
        test("Negative Test for Abandoned Baby Bearish Indicator - Index Out of Bounds") {
            val data =
                listOf(
                    OHLC(1, 10.0, 12.0, 9.0, 12.0),
                    OHLC(1, 15.0, 20.0, 14.0, 15.0),
                    OHLC(1, 12.0, 12.0, 9.0, 10.0),
                )
            AbandonedBabyPatternIndicator.bearIndicator(data, 1) shouldBe false
        }
    })
