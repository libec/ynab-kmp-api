package infrastructure.networking

import UserAuthentication
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class NetworkClient(
    private val httpClient: HttpClient,
    private val userAuthentication: UserAuthentication
) {

    // TODO: - Moving json plugin installation in here doesn't
    // seem to take effect on iOS as it fails on parsing. Investigate why.
//    init {
//        httpClient.config {
//            install(ContentNegotiation) {
//                json(makeJsonNegotiation())
//            }
//        }
//    }

    companion object {
        const val BASE_URL = "https://api.youneedabudget.com/v1"
    }

    suspend inline fun <reified T> get(
        endpoint: String,
        query: List<Pair<String, String>> = emptyList()
    ): T {
        return httpClient.get("$BASE_URL$endpoint") {
            // TODO: - Try to use Ktor plugin for Authentication
            header(HttpHeaders.Authorization, "Bearer ${userAuthentication.token}")
            query.forEach { (key, value) -> parameter(key, value) }
        }.body()
    }
}