package azody.atium.accounting

import azody.atium.domain.*

object Accounting {
    fun addTrade(
        trade: Trade,
        portfolio: Portfolio,
    ): Portfolio {
        if (trade.type === TradeType.BUY) {
            handleBuy(trade, portfolio)
        } else if (trade.type === TradeType.SELL) {
            throw NotImplementedError()
        } else if (trade.type === TradeType.INCOME) {
            throw NotImplementedError()
        }
        return portfolio
    }

    private fun handleBuy(
        trade: Trade,
        portfolio: Portfolio,
    ): Portfolio {
        val cashPosition = portfolio.cashPosition
        val previousPositions = portfolio.positions

        val previousPosition =
            previousPositions.firstOrNull {
                it.instrument == trade.instrument &&
                    it.counterInstrument == trade.counterInstrument
            }

        if (previousPosition == null) {
            return Portfolio(
                positions = previousPositions + trade.toNewPosition(),
                cashPosition = cashPosition.applyTrade(trade),
            )
        } else {
            val nonImpactedPositions =
                previousPositions.filter {
                    it.instrument != trade.instrument &&
                        it.counterInstrument != trade.counterInstrument
                }
            val newPositionLots = previousPosition.lots + trade.toNewLot()
            return Portfolio(
                positions = nonImpactedPositions + newPositionLots.toPositions(),
                cashPosition = cashPosition.applyTrade(trade),
            )
        }
    }
}
