import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import java.io.File
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json
import infrastructure.networking.NetworkClient

internal class NetworkClientMockFactory {
    fun makeMockedNetworkClient(
        mockedResponseFileName: String,
        endpoint: String,
        accessToken: String = "ae2e03aaew3"
    ): NetworkClient {
        return NetworkClient(
            httpClient = makeMockHttpClient(
                accessToken = accessToken,
                mockedResponseFileName = mockedResponseFileName,
                endpoint = endpoint
            ),
            userAuthentication = UserAuthentication(accessToken)
        )
    }

    private fun makeMockHttpClient(
        accessToken: String,
        mockedResponseFileName: String,
        endpoint: String
    ): HttpClient {
        val mockEngine = MockEngine { request ->
            val authHeader = request.headers[HttpHeaders.Authorization]
            println("HEADER: $authHeader")

            when (request.url.encodedPath) {
                "/v1/$endpoint" -> {
                    if (authHeader == "Bearer $accessToken") {
                        // TODO: - File is imported from java.io and iOS test can't run because of it.
                        val response =
                            File("src/commonTest/kotlin/MockedResponses/$mockedResponseFileName").readText()
                        respond(
                            content = ByteReadChannel(response),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    } else {
                        respond(
                            content = ByteReadChannel(""),
                            status = HttpStatusCode.Unauthorized,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }
                }

                else -> error("Unhandled ${request.url.encodedPath}")
            }
        }
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        return httpClient
    }
}
