package azody.atium.accounting

import azody.atium.domain.*
import java.math.BigDecimal
import java.util.*

object Accounting {
    fun addTrade(
        trade: Trade,
        portfolio: Portfolio,
    ): Portfolio {
        if (trade.type === TradeType.BUY) {
            return handleBuy(trade, portfolio)
        } else if (trade.type === TradeType.SELL) {
            return handleSell(trade, portfolio)
        } else if (trade.type === TradeType.INCOME) {
            return handleIncome(trade, portfolio)
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
                    it.instrument != trade.instrument ||
                        it.counterInstrument != trade.counterInstrument
                }
            val newPositionLots = previousPosition.lots + trade.toNewLot()
            return Portfolio(
                positions = nonImpactedPositions + newPositionLots.toPositions(),
                cashPosition = cashPosition.applyTrade(trade),
            )
        }
    }

    private fun handleSell(
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
                positions = (previousPositions + trade.toNewPosition()).filter { it.quantity != BigDecimal.ZERO },
                cashPosition = cashPosition.applyTrade(trade),
            )
        } else {
            // Only support FIFO at the moment
            val previousLots = previousPosition.lots
            val pQueue =
                if (portfolio.sellMethodology === SellMethodology.FIFO) {
                    PriorityQueue<Lot> { lot1, lot2 ->
                        lot1.acquisitionTime.compareTo(lot2.acquisitionTime)
                    }
                } else if (portfolio.sellMethodology === SellMethodology.LIFO) {
                    PriorityQueue<Lot> { lot1, lot2 ->
                        lot2.acquisitionTime.compareTo(lot1.acquisitionTime)
                    }
                } else {
                    throw NotImplementedError()
                }

            pQueue.addAll(previousLots)
            var remainingQuantity = trade.quantity
            val remainingLots = mutableListOf<Lot>()

            while (!pQueue.isEmpty()) {
                val lot = pQueue.remove()
                if (remainingQuantity > BigDecimal.ZERO) {
                    remainingQuantity -= lot.quantity
                    if (remainingQuantity <= BigDecimal.ZERO) {
                        remainingLots.add(
                            Lot(
                                instrument = lot.instrument,
                                counterInstrument = lot.counterInstrument,
                                price = lot.price,
                                quantity = remainingQuantity.abs(),
                                acquisitionTime = lot.acquisitionTime,
                                direction = lot.direction,
                            ),
                        )
                    }
                } else {
                    remainingLots.add(lot)
                }
            }

            if (remainingQuantity > BigDecimal.ZERO) {
                remainingLots.add(
                    Lot(
                        instrument = trade.instrument,
                        counterInstrument = trade.counterInstrument,
                        price = trade.price,
                        quantity = -remainingQuantity,
                        acquisitionTime = trade.businessTime,
                        direction = trade.direction,
                    ),
                )
            }
            val nonImpactedPositions =
                previousPositions.filter {
                    it.instrument != trade.instrument ||
                        it.counterInstrument != trade.counterInstrument
                }
            return Portfolio(
                positions = nonImpactedPositions + remainingLots.toPositions(),
                cashPosition = cashPosition.applyTrade(trade),
            )
        }
    }

    private fun handleIncome(
        trade: Trade,
        portfolio: Portfolio,
    ): Portfolio =
        Portfolio(
            positions = portfolio.positions,
            cashPosition = portfolio.cashPosition.applyTrade(trade),
        )
}
