package azody.atium.marketdata

import azody.atium.domain.OHLC
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializable
data class MarketDataResponse(
    val bars: Map<String, List<BarData>>,
)

@Serializable
data class BarData(
    val c: Double, // Close price
    val h: Double, // High price
    val l: Double, // Low price
    val n: Int, // Number of trades
    val o: Double, // Open price
    val t: String, // Timestamp
    val v: Int, // Volume
    val vw: Double, // Volume-weighted average price
) {
    // Convert string timestamp to LocalDateTime
    fun getTimestamp(): LocalDateTime = LocalDateTime.parse(t)
}

object AlpacaMarketData {
    val dotenv = dotenv()

    /**
     * param: symbol
     * param: startDate in format YYYY-MM-DD or YYYY-MM-DDThh:mm:ss
     * param: endDate in format YYYY-MM-DD or YYYY-MM-DDThh:mm:ss
     * param: timeFrame Alpaca Markets Timeframe
     */
    fun getOHLCData(
        symbol: String,
        startDate: String,
        endDate: String,
        timeframe: String,
    ): List<OHLC> {
        val apiKey = dotenv["ALPACA_API_KEY"] ?: throw IllegalStateException("ALPACA_API_KEY not set")
        val apiSecret = dotenv["ALPACA_SECRET_KEY"] ?: throw IllegalStateException("ALPACA_API_KEY not set")

        // Create an http client
        // Build the OkHttp client
        val client = OkHttpClient()

        val request =
            Request
                .Builder()
                .url(
                    "https://data.alpaca.markets/v2/stocks/bars?limit=10000&symbols=$symbol&timeframe=$timeframe&start=$startDate&end=$endDate&adjustment=raw&feed=iex&sort=asc",
                ).get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", apiKey)
                .addHeader("APCA-API-SECRET-KEY", apiSecret)
                .build()

        val response = client.newCall(request).execute()

        val json =
            Json {
                ignoreUnknownKeys = true // This will ignore any unknown keys in the JSON
            }

        val data = response.body!!.string()
        val marketData = json.decodeFromString<MarketDataResponse>(data)

        return marketData.bars[symbol]!!.toOHLC()
    }
}

fun List<BarData>.toOHLC(): List<OHLC> {
    val formatter = DateTimeFormatter.ISO_DATE_TIME

    return this.map {
        val localDateTime = LocalDateTime.parse(it.t, formatter)
        val epochMilli = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        OHLC(
            businessTime = epochMilli,
            open = it.o,
            high = it.h,
            low = it.l,
            close = it.c,
            volume = it.v.toDouble(),
        )
    }
}
