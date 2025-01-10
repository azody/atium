package azody.atium.domain

import java.math.BigDecimal

data class Portfolio(
    val positions: List<Position>,
    val cashPosition: CashPosition,
    val sellMethodology: SellMethodology = SellMethodology.FIFO,
)

data class Position(
    val instrument: String,
    val counterInstrument: String,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val direction: Direction,
    val lots: List<Lot>,
)

data class Lot(
    val instrument: String,
    val counterInstrument: String,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val acquisitionTime: Long,
    val direction: Direction,
)

data class CashPosition(
    val instrument: String,
    val quantity: BigDecimal,
)

enum class SellMethodology {
    FIFO,
    LIFO,
}

enum class Direction {
    LONG,
    SHORT,
}

fun CashPosition.applyTrade(trade: Trade): CashPosition {
    val tradeCashValue =
        if (trade.type === TradeType.SELL) {
            trade.price * trade.quantity
        } else if (trade.type === TradeType.BUY) {
            -trade.price * trade.quantity
        } else {
            trade.quantity // Assumes Income
        }

    return CashPosition(
        instrument = instrument,
        quantity = quantity + tradeCashValue,
    )
}

fun List<Lot>.toPositions(): List<Position> {
    if (this.isEmpty()) {
        return emptyList()
    }

    val groupedLots = this.groupBy { it.instrument + it.counterInstrument }
    val newPositions =
        groupedLots.values.map { lots ->

            val totalQuantity = lots.sumOf { it.quantity }
            val averagePrice =
                if (totalQuantity != BigDecimal.ZERO) {
                    lots.sumOf { it.price * it.quantity } / totalQuantity
                } else {
                    BigDecimal.ZERO
                }

            Position(
                instrument = lots[0].instrument,
                counterInstrument = lots[0].counterInstrument,
                price = averagePrice,
                quantity = totalQuantity,
                direction = lots[0].direction,
                lots = lots,
            )
        }

    return newPositions
}
