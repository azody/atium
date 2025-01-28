package azody.atium.marketdata

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AlpacaKtTest :
    FunSpec({

        test("Alpaca Market Data to toOHLC") {
            val bars =
                listOf(
                    BarData(
                        66.93,
                        67.5,
                        66.53,
                        808,
                        67.385,
                        "2024-12-02T14:30:00Z",
                        74106,
                        67.00182,
                    ),
                )

            val ohlc = bars.toOHLC()
            ohlc.size shouldBe bars.size
            ohlc[0].open shouldBe bars[0].o
            ohlc[0].high shouldBe bars[0].h
            ohlc[0].low shouldBe bars[0].l
            ohlc[0].close shouldBe bars[0].c
            ohlc[0].volume shouldBe bars[0].v
        }
    })
