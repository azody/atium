package azody.atium.indicator.ohlc.contrarian

import azody.atium.domain.OHLC
import azody.atium.domain.OHLCParameter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TowerPatternIndicatorTest :
    FunSpec({
        test("Positive Test for Tower Pattern Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MIN_BODY_HEIGHT.toString() to 4.0,
                )
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 7.0, 3.0, 4.0),
                    OHLC(5.0, 6.0, 2.0, 3.0),
                    OHLC(6.0, 7.0, 3.0, 4.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            TowerPatternIndicator.bullIndicator(data, 4, options) shouldBe true
        }
        test("Negative Test for Tower Pattern Bullish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MIN_BODY_HEIGHT.toString() to 4.0,
                )
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 7.0, 3.0, 4.0),
                    OHLC(5.0, 6.0, 2.0, 3.0),
                    OHLC(6.0, 7.0, 3.0, 4.0),
                    OHLC(5.0, 12.0, 3.0, 6.0),
                )
            TowerPatternIndicator.bullIndicator(data, 4, options) shouldBe false
        }
        test("Negative Test for Tower Pattern Bullish Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MIN_BODY_HEIGHT.toString() to 4.0,
                )
            val data =
                listOf(
                    OHLC(10.0, 12.0, 3.0, 5.0),
                    OHLC(6.0, 7.0, 3.0, 4.0),
                    OHLC(5.0, 6.0, 2.0, 3.0),
                    OHLC(6.0, 7.0, 3.0, 4.0),
                    OHLC(5.0, 12.0, 3.0, 10.0),
                )
            TowerPatternIndicator.bullIndicator(data, 3, options) shouldBe false
        }
        test("Positive Test for Tower Pattern Bearish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MIN_BODY_HEIGHT.toString() to 4.0,
                )
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(10.0, 17.0, 9.0, 12.0),
                    OHLC(11.0, 18.0, 10.0, 13.0),
                    OHLC(10.0, 17.0, 9.0, 12.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            TowerPatternIndicator.bearIndicator(data, 4, options) shouldBe true
        }
        test("Negative Test for Tower Pattern Bearish Indicator") {
            val options =
                mapOf(
                    OHLCParameter.MIN_BODY_HEIGHT.toString() to 4.0,
                )
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(10.0, 17.0, 9.0, 12.0),
                    OHLC(9.0, 16.0, 8.0, 11.0),
                    OHLC(10.0, 17.0, 9.0, 12.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            TowerPatternIndicator.bearIndicator(data, 4, options) shouldBe false
        }
        test("Negative Test for Tower Pattern Bearish Indicator - Index Out of Bounds") {
            val options =
                mapOf(
                    OHLCParameter.MIN_BODY_HEIGHT.toString() to 4.0,
                )
            val data =
                listOf(
                    OHLC(5.0, 12.0, 3.0, 10.0),
                    OHLC(10.0, 17.0, 9.0, 12.0),
                    OHLC(11.0, 18.0, 10.0, 13.0),
                    OHLC(10.0, 17.0, 9.0, 12.0),
                    OHLC(10.0, 12.0, 3.0, 5.0),
                )
            TowerPatternIndicator.bearIndicator(data, 3, options) shouldBe false
        }
    })
