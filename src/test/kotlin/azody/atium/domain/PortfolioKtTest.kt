package azody.atium.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class PortfolioKtTest :
    FunSpec({

        test("Cash Position Apply Trade - BUY") {
            val previousCashPosition =
                CashPosition(
                    instrument = "USD",
                    quantity = BigDecimal(1_000),
                )

            val trade =
                Trade(
                    businessTime = 123,
                    instrument = "AAPL",
                    counterInstrument = "USD",
                    settlementInstrument = "USD",
                    price = BigDecimal(100),
                    quantity = BigDecimal(2),
                    type = TradeType.BUY,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val newCashPosition = previousCashPosition.applyTrade(trade)
            previousCashPosition.quantity shouldBe BigDecimal(1_000)
            newCashPosition.quantity shouldBe BigDecimal(800)
        }

        test("Cash Position Apply Trade - SELL") {
            val previousCashPosition =
                CashPosition(
                    instrument = "USD",
                    quantity = BigDecimal(1_000),
                )

            val trade =
                Trade(
                    businessTime = 123,
                    instrument = "AAPL",
                    counterInstrument = "USD",
                    settlementInstrument = "USD",
                    price = BigDecimal(100),
                    quantity = BigDecimal(2),
                    type = TradeType.SELL,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val newCashPosition = previousCashPosition.applyTrade(trade)
            previousCashPosition.quantity shouldBe BigDecimal(1_000)
            newCashPosition.quantity shouldBe BigDecimal(1_200)
        }

        test("Cash Position Apply Trade - INCOME") {
            val previousCashPosition =
                CashPosition(
                    instrument = "USD",
                    quantity = BigDecimal(1_000),
                )

            val trade =
                Trade(
                    businessTime = 123,
                    instrument = "AAPL",
                    counterInstrument = "USD",
                    settlementInstrument = "USD",
                    price = BigDecimal(1),
                    quantity = BigDecimal(2),
                    type = TradeType.INCOME,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val newCashPosition = previousCashPosition.applyTrade(trade)
            previousCashPosition.quantity shouldBe BigDecimal(1_000)
            newCashPosition.quantity shouldBe BigDecimal(1_002)
        }

        test("Lots to Position - Same Security") {
            val lots =
                listOf(
                    Lot(
                        instrument = "AAPL",
                        counterInstrument = "USD",
                        price = BigDecimal(100),
                        quantity = BigDecimal(3),
                        acquisitionTime = 1,
                        direction = Direction.LONG,
                    ),
                    Lot(
                        instrument = "AAPL",
                        counterInstrument = "USD",
                        price = BigDecimal(200),
                        quantity = BigDecimal(1),
                        acquisitionTime = 2,
                        direction = Direction.LONG,
                    ),
                )
            val position = lots.toPositions()[0]

            position.instrument shouldBe "AAPL"
            position.price shouldBe BigDecimal(125)
            position.quantity shouldBe BigDecimal(4)
            position.lots.size shouldBe 2
        }

        test("Lots to Position - Multiple Securities") {
            val lots =
                listOf(
                    Lot(
                        instrument = "AAPL",
                        counterInstrument = "USD",
                        price = BigDecimal(100),
                        quantity = BigDecimal(3),
                        acquisitionTime = 1,
                        direction = Direction.LONG,
                    ),
                    Lot(
                        instrument = "X",
                        counterInstrument = "USD",
                        price = BigDecimal(200),
                        quantity = BigDecimal(1),
                        acquisitionTime = 2,
                        direction = Direction.LONG,
                    ),
                )
            val positions = lots.toPositions()
            positions.size shouldBe 2

            val aaplPosition = positions.filter { it.instrument == "AAPL" }.first()
            aaplPosition.instrument shouldBe "AAPL"
            aaplPosition.price shouldBe BigDecimal(100)
            aaplPosition.quantity shouldBe BigDecimal(3)
            aaplPosition.lots.size shouldBe 1

            val xPosition = positions.filter { it.instrument == "X" }.first()
            xPosition.instrument shouldBe "X"
            xPosition.price shouldBe BigDecimal(200)
            xPosition.quantity shouldBe BigDecimal(1)
            xPosition.lots.size shouldBe 1
        }

        test("Lots to Position - Empty List") {
            val lots = listOf<Lot>()
            val positions = lots.toPositions()
            positions.size shouldBe 0
        }

        test("Lots to Position - Zero Quantity") {
            val lots =
                listOf(
                    Lot(
                        instrument = "AAPL",
                        counterInstrument = "USD",
                        price = BigDecimal(100),
                        quantity = BigDecimal(0),
                        acquisitionTime = 1,
                        direction = Direction.LONG,
                    ),
                )
            val position = lots.toPositions()[0]

            position.instrument shouldBe "AAPL"
            position.price shouldBe BigDecimal(0)
            position.quantity shouldBe BigDecimal(0)
        }
    })
