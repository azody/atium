package azody.atium.marketdata

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class MarketDataResponse(
    val bars: List<Bar>,
)

@Serializable
data class Bar(
    val t: String, // Time
    val o: Double, // Open
    val h: Double, // High
    val l: Double, // Low
    val c: Double, // Close
    val v: Int, // Volume
)

fun main() =
    runBlocking {
        val apiKey = ""
        val apiSecret = ""

        val symbol = "PLTR"
        val startDate = "2024-12-01T00:00:00Z" // ISO 8601 Format
        val endDate = "2025-01-01T00:00:00Z"
        val timeframe = "1Day"

        // Create an http client
        val client =
            HttpClient(CIO) {
                install(ContentNegotiation) {
                    // json(Json { ignoreUnknownKeys = true })
                }
            }

        try {
            val response: HttpResponse =
                client.get("https://paper-api.alpaca.markets/v2/stocks/$symbol/bars") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $apiKey")
                    }
                    url {
                        parameters.append("start", startDate)
                        parameters.append("end", endDate)
                        parameters.append("timeframe", timeframe)
                    }
                }

            if (response.status.isSuccess()) {
                val marketDataResponse: MarketDataResponse = response.body()
                marketDataResponse.bars.forEach { bar ->
                    println("Date: ${bar.t}, Open: ${bar.o}, High: ${bar.h}, Low: ${bar.l}, Close: ${bar.c}")
                }
            }
        } catch (e: ClientRequestException) {
            println(e)
        } finally {
            client.close()
        }
    }
