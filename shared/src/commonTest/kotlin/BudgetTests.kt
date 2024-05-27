import features.account.Account
import features.budget.Budget
import fixtures.fixture
import infrastructure.formats.CurrencyFormat
import infrastructure.formats.DateFormat
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class BudgetTests {
    @Test
    fun `it downloads all budgets with injected access token`() = runBlocking {
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            mockedResponseFileName = "budget_response.json",
            endpoint = "budgets"
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val budgetRepository = ynabSession.getBudgetsRepository()

        budgetRepository.fetchAllBudgets()

        val expectedBudgets = listOf(
            Budget.fixture(
                id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                name = "Personal Budget",
                lastModifiedOn = "2024-05-18T09:54:28.054Z",
                firstMonth = "2024-05-01",
                lastMonth = "2024-05-31",
                accounts = listOf(
                    Account.fixture(
                        id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        name = "Checking Account",
                        type = "checking",
                        note = "Main checking account",
                        balance = -6942931520,
                        clearedBalance = 0,
                        unclearedBalance = 0,
                        transferPayeeId = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        lastReconciledAt = "2024-05-18T09:54:28.054Z"
                    )
                )
            ),
            Budget.fixture(
                id = "3fa85f64-5717-4562-b3fc-2c963f66afa7",
                name = "Business Budget",
                lastModifiedOn = "2023-05-11T09:54:28.054Z",
                firstMonth = "2023-02-01",
                lastMonth = "2023-05-31",
                accounts = listOf(
                    Account.fixture(
                        id = "3fa85f64-5717-4562-b3fc-2c963f66afa7",
                        name = "Business Account",
                        type = "checking",
                        onBudget = true,
                        closed = false,
                        note = "Main business account",
                        balance = 1000000,
                        clearedBalance = 1000000,
                        unclearedBalance = 0,
                        transferPayeeId = "3fa85f64-5717-4562-b3fc-2c963f66afa7",
                        lastReconciledAt = "2024-05-18T09:54:28.054Z",
                    )
                )
            )
        )

        val expectedDefaultBudget = Budget.fixture(
            id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            name = "Default Budget",
            lastModifiedOn = "2024-05-18T09:54:28.054Z",
            firstMonth = "2024-05-01",
            lastMonth = "2024-05-31",
            accounts = listOf(
                Account.fixture(
                    id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    name = "Savings Account",
                    type = "savings",
                    note = "Emergency savings",
                    balance = 500000,
                    clearedBalance = 500000,
                    unclearedBalance = 0,
                    transferPayeeId = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    lastReconciledAt = "2024-05-18T09:54:28.054Z",
                )
            )
        )

        assertEquals(expectedBudgets, budgetRepository.budgets.value)
        assertEquals(expectedDefaultBudget, budgetRepository.selectedBudget.value)
    }
}
