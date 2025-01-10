package azody.atium.domain

import java.math.BigDecimal

data class Trade(
    val businessTime: Long,
    val instrument: String,
    val counterInstrument: String,
    val settlementInstrument: String,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val type: TradeType,
    val tradeSubType: TradeSubType,
    val direction: Direction,
)

enum class TradeType {
    BUY,
    SELL,
    INCOME,
}

enum class TradeSubType {
    NONE,
    DIV,
    EXPIRY,
}

fun Trade.toNewPosition(): Position {
    val quantity = if (type === TradeType.SELL) -this.quantity else this.quantity
    return Position(
        instrument = this.instrument,
        counterInstrument = this.counterInstrument,
        price = this.price,
        quantity = quantity,
        direction = this.direction,
        lots = listOf(this.toNewLot()),
    )
}

fun Trade.toNewLot(): Lot =
    Lot(
        instrument = this.instrument,
        counterInstrument = this.counterInstrument,
        price = this.price,
        quantity = this.quantity,
        acquisitionTime = this.businessTime,
        direction = this.direction,
    )
