import features.account.Account
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountsTests: MockedResponseTests() {

    @Test
    fun `it downloads and stores accounts`() = runBlocking {
        val mockedResponses = listOf(Pair("budgets/$budgetId/accounts", "accounts.json"))
        val ynabSession = makeYnabSession(mockedResponses)
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