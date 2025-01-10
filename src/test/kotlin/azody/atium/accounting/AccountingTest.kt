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

        test("AddTrade - Sell on Empty Portfolio") {
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
                    type = TradeType.SELL,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(1_200)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"

            val applPosition = resultingPortfolio.positions.filter { it.instrument == "AAPL" }[0]!!

            applPosition.instrument shouldBe "AAPL"
            applPosition.counterInstrument shouldBe "USD"
            applPosition.lots.size shouldBe 1
            applPosition.price shouldBe BigDecimal(100)
            applPosition.quantity shouldBe BigDecimal(-2)
        }

        test("AddTrade - Sell on Portfolio - FIFO Full Lot") {
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

            val startingPortfolio =
                Portfolio(
                    positions = lots.toPositions(),
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
                    quantity = BigDecimal(3),
                    type = TradeType.SELL,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(1_300)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"

            val applPosition = resultingPortfolio.positions.filter { it.instrument == "AAPL" }[0]!!

            applPosition.instrument shouldBe "AAPL"
            applPosition.counterInstrument shouldBe "USD"
            applPosition.price shouldBe BigDecimal(200)
            applPosition.quantity shouldBe BigDecimal(1)
        }

        test("AddTrade - Sell on Portfolio - FIFO Partial Lot") {
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

            val startingPortfolio =
                Portfolio(
                    positions = lots.toPositions(),
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
                    type = TradeType.SELL,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(1_200)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"

            val applPosition = resultingPortfolio.positions.filter { it.instrument == "AAPL" }[0]!!

            applPosition.instrument shouldBe "AAPL"
            applPosition.counterInstrument shouldBe "USD"
            applPosition.price shouldBe BigDecimal(150)
            applPosition.quantity shouldBe BigDecimal(2)
        }

        test("AddTrade - Sell on Portfolio - LIFO Full Lot") {
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

            val startingPortfolio =
                Portfolio(
                    positions = lots.toPositions(),
                    cashPosition =
                        CashPosition(
                            instrument = "USD",
                            quantity = BigDecimal(1_000),
                        ),
                    sellMethodology = SellMethodology.LIFO,
                )

            val newTrade =
                Trade(
                    businessTime = 1,
                    instrument = "AAPL",
                    counterInstrument = "USD",
                    settlementInstrument = "USD",
                    price = BigDecimal(100),
                    quantity = BigDecimal(1),
                    type = TradeType.SELL,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(1_100)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"

            val applPosition = resultingPortfolio.positions.filter { it.instrument == "AAPL" }[0]!!

            applPosition.instrument shouldBe "AAPL"
            applPosition.counterInstrument shouldBe "USD"
            applPosition.price shouldBe BigDecimal(100)
            applPosition.quantity shouldBe BigDecimal(3)
        }

        test("AddTrade - Sell on Portfolio - Oversell") {
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

            val startingPortfolio =
                Portfolio(
                    positions = lots.toPositions(),
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
                    quantity = BigDecimal(5),
                    type = TradeType.SELL,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(1_500)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"

            val applPosition = resultingPortfolio.positions.filter { it.instrument == "AAPL" }[0]!!

            applPosition.instrument shouldBe "AAPL"
            applPosition.counterInstrument shouldBe "USD"
            applPosition.price shouldBe BigDecimal(100)
            applPosition.quantity shouldBe BigDecimal(-1)
        }

        test("AddTrade - Income on Portfolio") {

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
                    instrument = "USD",
                    counterInstrument = "USD",
                    settlementInstrument = "USD",
                    price = BigDecimal(1),
                    quantity = BigDecimal(500),
                    type = TradeType.INCOME,
                    tradeSubType = TradeSubType.NONE,
                    direction = Direction.LONG,
                )

            val resultingPortfolio = Accounting.addTrade(trade = newTrade, portfolio = startingPortfolio)

            resultingPortfolio.cashPosition.quantity shouldBe BigDecimal(1_500)
            resultingPortfolio.cashPosition.instrument shouldBe "USD"
        }
    })
