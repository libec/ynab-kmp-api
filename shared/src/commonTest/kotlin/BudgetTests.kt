import features.account.Account
import features.budget.Budget
import features.category.Category
import features.category.CategoryGroup
import features.month.Month
import features.payee.Payee
import features.payee.PayeeLocation
import features.transaction.ScheduledSubtransaction
import features.transaction.ScheduledTransaction
import features.transaction.Subtransaction
import features.transaction.Transaction
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import mocks.NetworkClientMockFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class BudgetTests {
    @Test
    fun `it downloads all budgets with injected access token`() = runBlocking {
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            mockedResponseFileName = "budgets.json", endpoint = "budgets"
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val budgetRepository = ynabSession.getBudgetsRepository()

        budgetRepository.fetchAllBudgets()

        val expectedBudgets = listOf(
            Budget.fixture(
                budgetDetail = Budget.BudgetDetail.fixture(
                    accounts = listOf(Account.fixture())
                )
            ), Budget.fixture(
                id = "budg-id-bus33",
                name = "Business Budget",
                budgetDetail = Budget.BudgetDetail.fixture(
                    accounts = listOf(
                        Account.fixture(
                            id = "bus-acc-id4",
                            name = "Business Account",
                            type = "checking",
                            note = "Main business account",
                        )
                    )
                )
            )
        )

        val expectedDefaultBudget = Budget.fixture(
            id = "default-budg1",
            name = "Default Budget",
            budgetDetail = Budget.BudgetDetail.fixture(
                accounts = listOf(
                    Account.fixture(
                        id = "def-acc19",
                        name = "Savings Account",
                        type = "savings",
                        note = "Emergency savings",
                        balance = 500000,
                        clearedBalance = 500000,
                        unclearedBalance = 0,
                    )
                )
            )
        )

        assertEquals(expectedBudgets, budgetRepository.budgets.value)
        assertEquals(expectedDefaultBudget, budgetRepository.selectedBudget.value)
    }

    @Test
    fun `it fetches a budget with detail`() = runBlocking {
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            mockedResponseFileName = "budget_detail.json", endpoint = "budgets/id3"
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val budgetRepository = ynabSession.getBudgetsRepository()

        val expectedBudget = Budget.fixture(
            budgetDetail = Budget.BudgetDetail.fixture(
                accounts = listOf(Account.fixture()),
                payees = listOf(Payee.fixture()),
                payeeLocations = listOf(PayeeLocation.fixture()),
                categoryGroups = listOf(CategoryGroup.fixture()),
                categories = listOf(Category.fixture()),
                months = listOf(Month.fixture()),
                transactions = listOf(Transaction.fixture()),
                subtransactions = listOf(Subtransaction.fixture()),
                scheduledTransactions = listOf(ScheduledTransaction.fixture()),
                scheduledSubtransactions = listOf(ScheduledSubtransaction.fixture())
            )
        )

        val actualBudget = budgetRepository.fetchBudgetWithDetail("id3")

        assertEquals(expectedBudget, actualBudget)
    }
}
