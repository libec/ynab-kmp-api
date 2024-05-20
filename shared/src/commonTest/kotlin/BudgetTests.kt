import Budget.BudgetsRestResource
import NetworkClient
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BudgetTests {

    @Test
    fun `it downloads all budgets with injected access token`() = runBlocking {
        val resource =
            BudgetsRestResource(
                makeMockedNetworkClient(
                    mockedResponseFileName = "BudgetsResponse.json",
                    endpoint = "budgets"
                )
            )

        val budgetsResponse = resource.getAllBudgets()

        assertEquals(
            budgetsResponse.budgets.map { it.id },
            listOf(
                "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "123e4567-e89b-12d3-a456-426614174000",
            )
        )
    }

    private fun makeMockedNetworkClient(
        mockedResponseFileName: String,
        endpoint: String
    ): NetworkClient {
        val accessToken = "ae2e03aaew3"
        return NetworkClient(
            httpClient = makeMockHttpClient(
                accessToken = accessToken,
                mockedResponseFileName = mockedResponseFileName,
                endpoint = endpoint
            ),
            session = Session(accessToken)
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
