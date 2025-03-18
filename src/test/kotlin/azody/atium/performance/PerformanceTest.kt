package azody.atium.performance

import azody.atium.domain.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class PerformanceTest :
    FunSpec({

        test("getHitRatio Single Asset") {

            val trades =
                listOf(
                    Trade(
                        businessTime = 1,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("100"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 2,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("200"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 3,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("200"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 4,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("200"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 5,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 6,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 7,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 8,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                )

            Performance.getHitRatio(trades) shouldBe BigDecimal(0.25)
        }

        test("getHitRatio Single Asset - ") {

            val trades =
                listOf(
                    Trade(
                        businessTime = 1,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("100"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 2,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("200"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 3,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("200"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 4,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("200"),
                        quantity = BigDecimal("1"),
                        type = TradeType.BUY,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 5,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 6,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 7,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                    Trade(
                        businessTime = 8,
                        instrument = "BTC",
                        counterInstrument = "USD",
                        settlementInstrument = "USD",
                        price = BigDecimal("150"),
                        quantity = BigDecimal("1"),
                        type = TradeType.SELL,
                        tradeSubType = TradeSubType.NONE,
                        direction = Direction.LONG,
                    ),
                )

            Performance.getHitRatio(trades) shouldBe BigDecimal(0.25)
        }

        test("getPercentChange Positive Gain") {
            val startingPortfolio =
                Portfolio(
                    cashPosition =
                        CashPosition(
                            instrument = "USD",
                            quantity = BigDecimal("100"),
                        ),
                    positions = emptyList(),
                )
            val endPortfolio =
                Portfolio(
                    cashPosition =
                        CashPosition(
                            instrument = "USD",
                            quantity = BigDecimal("120"),
                        ),
                    positions = emptyList(),
                )

            Performance.getPercentChange(startingPortfolio, endPortfolio) shouldBe BigDecimal("0.2")
        }

        test("getPercentChange Negative Gain") {
            val startingPortfolio =
                Portfolio(
                    cashPosition =
                        CashPosition(
                            instrument = "USD",
                            quantity = BigDecimal("100"),
                        ),
                    positions = emptyList(),
                )
            val endPortfolio =
                Portfolio(
                    cashPosition =
                        CashPosition(
                            instrument = "USD",
                            quantity = BigDecimal("80"),
                        ),
                    positions = emptyList(),
                )

            Performance.getPercentChange(startingPortfolio, endPortfolio) shouldBe BigDecimal("-0.2")
        }
    })
