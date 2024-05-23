import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class NetworkClient(
    val httpClient: HttpClient,
    val userAuthentication: UserAuthentication
) {

    companion object {
        const val baseUrl = "https://api.youneedabudget.com/v1"
    }

    suspend inline fun <reified T> get(endpoint: String): T {
        return httpClient.get("$baseUrl$endpoint") {
            header(HttpHeaders.Authorization, "Bearer ${userAuthentication.token}")
        }.body()
    }
}