package azody.atium.indicator.ohlc.trendfollowing

import azody.atium.domain.OHLC
import azody.atium.domain.OHLCParameter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ThreeCandlesPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Three Candles Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 9.0, 4.0, 8.0),
                    OHLC(1, 6.0, 10.0, 5.0, 9.0),
                    OHLC(1, 7.0, 11.0, 6.0, 10.0),
                    OHLC(1, 8.0, 12.0, 7.0, 11.0),
                )
            ThreeCandlesPatternIndicator.bullIndicator(
                data,
                3,
                mapOf(OHLCParameter.MIN_BODY_HEIGHT.toString() to 0.5),
            ) shouldBe true
        }
        test("Negative Test for Three Candles Pattern Bullish Indicator") {
            val data =
                listOf(
                    OHLC(1, 5.0, 9.0, 4.0, 5.2),
                    OHLC(1, 6.0, 10.0, 5.0, 6.2),
                    OHLC(1, 7.0, 11.0, 6.0, 10.0),
                    OHLC(1, 8.0, 12.0, 7.0, 11.0),
                )
            ThreeCandlesPatternIndicator.bullIndicator(
                data,
                3,
                mapOf(OHLCParameter.MIN_BODY_HEIGHT.toString() to 0.5),
            ) shouldBe false
        }
        test("Negative Test for Three Candles Pattern Bullish Indicator - Index Out of Bounds Exception") {
            val data =
                listOf(
                    OHLC(1, 5.0, 9.0, 4.0, 8.0),
                    OHLC(1, 6.0, 10.0, 5.0, 9.0),
                    OHLC(1, 7.0, 11.0, 6.0, 10.0),
                    OHLC(1, 8.0, 12.0, 7.0, 11.0),
                )
            ThreeCandlesPatternIndicator.bullIndicator(
                data,
                2,
                mapOf(OHLCParameter.MIN_BODY_HEIGHT.toString() to 0.5),
            ) shouldBe false
        }
        test("Positive Test for Three Candles Pattern Bearish Indicator - Index Out of Bounds Exception") {
            val data =
                listOf(
                    OHLC(1, 10.0, 11.0, 7.0, 8.0),
                    OHLC(1, 9.0, 10.0, 6.0, 7.0),
                    OHLC(1, 8.0, 9.0, 5.0, 6.0),
                    OHLC(1, 7.0, 8.0, 4.0, 5.0),
                )
            ThreeCandlesPatternIndicator.bearIndicator(
                data,
                3,
                mapOf(OHLCParameter.MIN_BODY_HEIGHT.toString() to 0.5),
            ) shouldBe true
        }
        test("Negative Test for Three Candles Pattern Bearish Indicator") {
            val data =
                listOf(
                    OHLC(1, 10.0, 11.0, 7.0, 8.0),
                    OHLC(1, 9.0, 10.0, 6.0, 8.8),
                    OHLC(1, 8.0, 9.0, 5.0, 6.0),
                    OHLC(1, 7.0, 8.0, 4.0, 5.0),
                )
            ThreeCandlesPatternIndicator.bearIndicator(
                data,
                3,
                mapOf(OHLCParameter.MIN_BODY_HEIGHT.toString() to 0.5),
            ) shouldBe false
        }
        test("Negative Test for Three Candles Pattern Bearish Indicator - Index Out of Bounds Exception") {
            val data =
                listOf(
                    OHLC(1, 10.0, 11.0, 7.0, 8.0),
                    OHLC(1, 9.0, 10.0, 6.0, 7.0),
                    OHLC(1, 8.0, 9.0, 5.0, 6.0),
                    OHLC(1, 7.0, 8.0, 4.0, 5.0),
                )
            ThreeCandlesPatternIndicator.bearIndicator(
                data,
                2,
                mapOf(OHLCParameter.MIN_BODY_HEIGHT.toString() to 0.5),
            ) shouldBe false
        }
    })
