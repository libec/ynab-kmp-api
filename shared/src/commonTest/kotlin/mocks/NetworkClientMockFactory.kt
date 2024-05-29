package mocks

import UserAuthentication
import infrastructure.networking.NetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import java.io.File

internal class NetworkClientMockFactory {
    fun makeMockedNetworkClient(
        mockedResponses: List<Pair<String, String>>,
        accessToken: String = "ae2e03aaew3"
    ): NetworkClient {
        return NetworkClient(
            httpClient = makeMockHttpClient(
                accessToken = accessToken,
                mockedResponses = mockedResponses
            ),
            userAuthentication = UserAuthentication(accessToken)
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun makeMockHttpClient(
        accessToken: String,
        mockedResponses: List<Pair<String, String>>
    ): HttpClient {
        val mockEngine = MockEngine { request ->
            val authHeader = request.headers[HttpHeaders.Authorization]
            val mockedResponse =
                mockedResponses.find { "/v1/${it.first}" == request.url.encodedPath }

            if (mockedResponse != null) {
                if (authHeader == "Bearer $accessToken") {
                    // TODO: - File is imported from java.io and iOS test can't run because of it.
                    val response =
                        File("src/commonTest/kotlin/mocked_responses/${mockedResponse.second}").readText()
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
            } else {
                error("Unhandled ${request.url.encodedPath}")
            }
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    namingStrategy = JsonNamingStrategy.SnakeCase
                    explicitNulls = false
                })
            }
        }
        return httpClient
    }
}
