package infrastructure.networking

import UserAuthentication
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

internal class NetworkClient(
    internal val httpClient: HttpClient,
    internal val userAuthentication: UserAuthentication
) {

    companion object {
        const val baseUrl = "https://api.youneedabudget.com/v1"
    }

    suspend inline fun <reified T> get(endpoint: String, query: List<Pair<String, String>> = emptyList()): T {
        return httpClient.get("$baseUrl$endpoint") {
            header(HttpHeaders.Authorization, "Bearer ${userAuthentication.token}")
            query.forEach { (key, value) -> parameter(key, value) }
        }.body()
    }
}