package azody.atium.domain

import io.kotest.core.spec.style.FunSpec
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
        }

        test("toPosition") { }
    })
