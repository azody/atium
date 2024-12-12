package azody.atium.domain

import azody.atium.ohlc.OHLC

interface Indicator {

    fun bullIndicator(data: List<OHLC>, i: Int, option: Map<String, Double> = emptyMap()) : Boolean

    fun bearIndicator(data: List<OHLC>, i: Int,  option: Map<String, Double> = emptyMap()) : Boolean

}