import Budget.BudgetsRepositoryImpl
import Budget.BudgetsResource
import Budget.BudgetsRestResource
import TestDoubles.MockBudgetsResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
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
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel("""{"data": {"budgets": [{"id": "103"}, {"id": "913"}, {"id": "32104"}]}}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }
        val resource = BudgetsRestResource(httpClient)

        val budgets = resource.getAllBudgets()

        assertEquals(budgets.map { it.id }, listOf("103", "913", "32104"))
    }
}