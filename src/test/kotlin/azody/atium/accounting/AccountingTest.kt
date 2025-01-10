package azody.atium.accounting

import azody.atium.domain.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class AccountingTest :
    FunSpec({

        test("AddTrade - Buy on Empty Portfolio") {
            val startingPortfolio =
                Portfolio(
                    positions = listOf(),
                    cashPosition =
                        CashPosition(
                            instrument = "USD",
                            quantity = BigDecimal(1_000),
                        ),
                )

            val newTrade =
                Trade(
                    businessTime = 1,
                    instrument = "AAPL",
                    counterInstrument = "USD",
                    settlementInstrument = "USD",
                    price = BigDecimal(100),
                    quantity = BigDecimal(2),
                    type = TradeType.BUY,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(800)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"

            val applPosition = resultingPortfolio.positions.filter { it.instrument == "AAPL" }[0]!!

            applPosition.instrument shouldBe "AAPL"
            applPosition.counterInstrument shouldBe "USD"
            applPosition.lots.size shouldBe 1
            applPosition.price shouldBe BigDecimal(100)
            applPosition.quantity shouldBe BigDecimal(2)
        }

        test("AddTrade - Buy on Multi Asset Portfolio") {
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
            val startingPortfolio =
                Portfolio(
                    positions = positions,
                    cashPosition =
                        CashPosition(
                            instrument = "USD",
                            quantity = BigDecimal(1_000),
                        ),
                )

            val newTrade =
                Trade(
                    businessTime = 2,
                    instrument = "AAPL",
                    counterInstrument = "USD",
                    settlementInstrument = "USD",
                    price = BigDecimal(100),
                    quantity = BigDecimal(2),
                    type = TradeType.BUY,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(800)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"

            val applPosition = resultingPortfolio.positions.filter { it.instrument == "AAPL" }[0]!!

            applPosition.instrument shouldBe "AAPL"
            applPosition.counterInstrument shouldBe "USD"
            applPosition.lots.size shouldBe 2
            applPosition.price shouldBe BigDecimal(100)
            applPosition.quantity shouldBe BigDecimal(5)

            val xPosition = resultingPortfolio.positions.filter { it.instrument == "X" }[0]!!

            xPosition.instrument shouldBe "X"
            xPosition.counterInstrument shouldBe "USD"
            xPosition.lots.size shouldBe 1
            xPosition.price shouldBe BigDecimal(200)
            xPosition.quantity shouldBe BigDecimal(1)
        }
    })
