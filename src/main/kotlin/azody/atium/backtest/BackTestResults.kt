package azody.atium.backtest

import azody.atium.domain.Portfolio
import azody.atium.domain.Trade
import java.math.BigDecimal

data class BackTestResults(
    val startingPortfolio: Portfolio,
    val resultingPortfolio: Portfolio,
    val trades: List<Trade>,
    val finalValues: Map<String, BigDecimal>,
)
