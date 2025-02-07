package azody.atium.backtest

import azody.atium.domain.Portfolio
import azody.atium.domain.Trade

data class BackTestResults(
    val portfolioSeries: Map<Long, Portfolio>, // Time series of portfolio
    val tradeSeries: Map<Long, List<Trade>>, // Time series of trades
)
