import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class BudgetTests {
    @Test
    fun `it downloads all budgets with injected access token`() = runBlocking {
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            mockedResponseFileName = "BudgetsResponse.json",
            endpoint = "budgets"
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val budgetRepository = ynabSession.getBudgetsRepository()

        budgetRepository.fetchAllBudgets()

        assertEquals(
            budgetRepository.budgets.value.map { it.id },
            listOf(
                "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "123e4567-e89b-12d3-a456-426614174000",
            )
        )
        assertEquals(
            budgetRepository.selectedBudget.value?.id,
            "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
    }
}
