import features.account.Account
import features.budget.Budget
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import mocks.NetworkClientMockFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountsTests {

    @Test
    fun `it downloads and stores accounts`() = runBlocking {
        val budgetId = Budget.fixture().id
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            listOf(Pair("budgets/$budgetId/accounts", "accounts.json"))
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val accountsRepository = ynabSession.getAccountsRepository()

        accountsRepository.fetchAccounts(budgetId)

        val expectedAccounts = listOf(
            Account.fixture(),
            Account.fixture(
                id = "bus-acc-id434z",
                name = "Business Account",
                type = "business",
                onBudget = false,
                closed = true,
                note = "Main business account",
                balance = 1250000,
                clearedBalance = 1200000,
                unclearedBalance = 1350000,
                transferPayeeId = "transfer-business-19",
                debtOriginalBalance = 19500
            )
        )

        assertEquals(expectedAccounts, accountsRepository.accounts.value)
    }
}