import Budget.BudgetsRepositoryImpl
import Budget.BudgetsResource
import Budget.MockBudgetsResource
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
}