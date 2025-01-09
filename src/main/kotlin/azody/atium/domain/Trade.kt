package azody.atium.domain

import java.math.BigDecimal

data class Trade(
    val businessTime: Long,
    val instrument: String,
    val counterInstrument: String,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val type: TradeType,
    val tradeSubType: TradeSubType,
)

enum class TradeType {
    BUY,
    SELL,
    CASH,
}

enum class TradeSubType {
    NONE,
    DIV,
    EXPIRY,
}
