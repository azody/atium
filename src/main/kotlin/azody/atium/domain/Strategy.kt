package azody.atium.domain

import java.math.BigDecimal

enum class ExitStrategy {
    PROFIT_LOSS_TARGETS,  // Exit based on profit/loss targets
    INDICATOR_SIGNAL,     // Exit based on bearish indicator
    BOTH                  // Exit on either profit/loss targets or bearish indicator
}

data class Strategy(
    val startingPortfolio: Portfolio,
    val indicator: Indicator,
    val instrument: String,
    val allowShort: Boolean = false,
    val allowMargin: Boolean = false,
    val profitTarget: Double? = null,  // Exit when profit reaches this percentage (e.g. 0.05 for 5%)
    val stopLoss: Double? = null,      // Exit when loss reaches this percentage (e.g. 0.03 for 3%)
    val exitStrategy: ExitStrategy = ExitStrategy.INDICATOR_SIGNAL,  // Default to original behavior
    val maxPositionSize: BigDecimal = BigDecimal(5)  // Maximum number of units to hold at once
)
