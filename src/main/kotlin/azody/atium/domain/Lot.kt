package azody.atium.domain

import java.math.BigDecimal

data class Lot(
    val instrument: String,
    val counterInstrument: String,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val acquisitionTime: Long,
    val direction: Direction,
)

enum class Direction {
    LONG,
    SHORT,
}
