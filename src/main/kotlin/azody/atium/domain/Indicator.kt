package azody.atium.domain

interface Indicator {
    fun bullIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double> = emptyMap(),
    ): Boolean

    fun bearIndicator(
        data: List<OHLC>,
        i: Int,
        options: Map<String, Double> = emptyMap(),
    ): Boolean
}
