package azody.atium.domain

data class Strategy(
    val startingPortfolio: Portfolio,
    val indicator: Indicator,
    val instrument: String,
    val allowShort: Boolean = false,
)
