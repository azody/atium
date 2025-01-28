package azody.atium.marketdata

import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDateTime

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

fun main() {
    val dotenv = dotenv()

    val apiKey = dotenv["ALPACA_API_KEY"] ?: throw IllegalStateException("ALPACA_API_KEY not set")
    val apiSecret = dotenv["ALPACA_SECRET_KEY"] ?: throw IllegalStateException("ALPACA_API_KEY not set")

    val symbol = "PLTR"
    val startDate = "2024-12-01" // ISO 8601 Format
    val endDate = "2025-01-01"
    val timeframe = "10Min"

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
            .addHeader("APCA-API-KEY-ID", "PK7FUUETM5D3QFDBWVZ2")
            .addHeader("APCA-API-SECRET-KEY", "PaVnFSfDhez3UwBKlxfDb9CIx0zrfaHJ15acKr8A")
            .build()

    val response = client.newCall(request).execute()

    val json =
        Json {
            ignoreUnknownKeys = true // This will ignore any unknown keys in the JSON
        }

    val data = response.body!!.string()
    val marketData = json.decodeFromString<MarketDataResponse>(data)

    println(marketData)
}
