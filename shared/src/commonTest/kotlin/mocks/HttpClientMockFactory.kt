package mocks

import UserAuthentication
import infrastructure.networking.NetworkClient
import infrastructure.networking.makeJsonNegotiation
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import java.io.File

internal class HttpClientMockFactory {
    fun makeMockedHttpClient(mockedResponses: List<Pair<String, String>>): HttpClient {
        val mockEngine = MockEngine { request ->
            val mockedResponse =
                mockedResponses.find { "/v1/${it.first}" == request.url.encodedPath }

            if (mockedResponse != null) {
                val response =
                    loadJsonResponse(mockedResponse.second)
                makeSuccessResponse(response)
            } else {
                makeErrorResponse(HttpStatusCode.NotFound)
            }
        }
        return makeHttpClient(mockEngine)
    }

    fun makeMockedHttpClient(accessToken: String, mockedResponse: String): HttpClient {
        val mockEngine = MockEngine { request ->
            val authHeader = request.headers[HttpHeaders.Authorization]
            if (authHeader == "Bearer $accessToken") {
                val response =
                    loadJsonResponse(mockedResponse)
                makeSuccessResponse(response)
            } else {
                makeErrorResponse(HttpStatusCode.Unauthorized)
            }
        }

        return makeHttpClient(mockEngine)
    }

    private fun loadJsonResponse(path: String): String {
        // TODO: - File is imported from java.io and iOS test can't run because of it.
        // try to use expected method and use actual where iOS would use Bundle to load resources
        val json: String =
            File("src/commonTest/kotlin/mocked_responses/${path}").readText()
        return json

    }

    private fun MockRequestHandleScope.makeErrorResponse(statusCode: HttpStatusCode) =
        respond(
            content = ByteReadChannel(""),
            status = statusCode,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )

    private fun MockRequestHandleScope.makeSuccessResponse(content: String = "") = respond(
        content = ByteReadChannel(content),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )

    private fun makeNetworkClient(
        mockEngine: MockEngine,
        accessToken: String
    ): NetworkClient {
        return NetworkClient(
            httpClient = makeHttpClient(mockEngine),
            userAuthentication = UserAuthentication(accessToken)
        )
    }

    private fun makeHttpClient(mockEngine: MockEngine): HttpClient {
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(makeJsonNegotiation())
            }
        }
        return httpClient
    }
}
