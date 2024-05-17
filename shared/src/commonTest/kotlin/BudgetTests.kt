import Budget.BudgetsRepositoryImpl
import Budget.BudgetsResource
import Budget.BudgetsRestResource
import TestDoubles.MockBudgetsResource
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import java.io.File
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BudgetTests {
    @BeforeTest
    fun setup() {
        YnabApi.login("test token")
    }

    @Test
    fun `it downloads and parses all budgets when injected by hand`() = runBlocking {
        val repository = BudgetsRepositoryImpl(MockBudgetsResource())
        repository.fetchAllBudgets()
        val ids: List<String> = repository.budgets.value.map { it.id }

        assertEquals(ids, listOf("103", "913", "32104"))
    }

    @Test
    fun `it downloads and parses all budgets using faked koin dependencies`() = runBlocking {
        YnabApi.loginScope?.declare<BudgetsResource>(MockBudgetsResource())
        val repository = YnabApi.getBudgetsRepository()
        repository.fetchAllBudgets()
        val ids: List<String> = repository.budgets.value.map { it.id }

        assertEquals(ids, listOf("103", "913", "32104"))
    }

    @Test
    fun `it downloads all budgets`() = runBlocking {
        val mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/v1/budgets" -> {
                    val response = File("src/commonTest/kotlin/MockedResponses/BudgetsResponse.json").readText()
                    respond(
                        content = ByteReadChannel(response),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                else -> error("Unhandled ${request.url.encodedPath}")
            }
        }
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }
        val resource = BudgetsRestResource(httpClient)

        val budgets = resource.getAllBudgets()

        assertEquals(budgets.map { it.id }, listOf("103", "9002", "32104"))
    }
}