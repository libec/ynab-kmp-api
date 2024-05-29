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
import fixtures.summaryFixture
import kotlinx.coroutines.runBlocking
import mocks.NetworkClientMockFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class BudgetsTests {

    @Test
    fun `it downloads all budgets with injected access token`() = runBlocking {
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            listOf(Pair("budgets", "budgets.json"))
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
            listOf(Pair("budgets/id3", "budget_detail.json"))
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
                transactions = listOf(Transaction.summaryFixture()),
                subtransactions = listOf(Subtransaction.fixture()),
                scheduledTransactions = listOf(ScheduledTransaction.fixture()),
                scheduledSubtransactions = listOf(ScheduledSubtransaction.fixture())
            )
        )

        val actualBudget = budgetRepository.fetchBudgetWithDetail("id3")

        assertEquals(expectedBudget, actualBudget)
    }

    @Test
    fun `fetching budget with a detail overwrites the existing budget with the same id`() = runBlocking {
        val budgetId = "budg-id-dbaw4rj"
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            listOf(Pair("budgets", "budgets.json"), Pair("budgets/$budgetId", "budget_detail.json"))
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val budgetRepository = ynabSession.getBudgetsRepository()
        budgetRepository.fetchAllBudgets()

        val simpleBudget = budgetRepository.budgets.value.find { it.id == budgetId }

        val expectedEmptyDetail = Budget.BudgetDetail.fixture(
            accounts = listOf(Account.fixture())
        )
        assertEquals(expectedEmptyDetail, simpleBudget?.budgetDetail)
        assertEquals(budgetRepository.budgets.value.size, 2)
        budgetRepository.fetchBudgetWithDetail(budgetId)
        val budgetWithDetail = budgetRepository.budgets.value.find { it.id == budgetId }

        assertEquals(budgetRepository.budgets.value.size, 2)

        val expectedDetail = Budget.BudgetDetail.fixture(
            accounts = listOf(Account.fixture()),
            payees = listOf(Payee.fixture()),
            payeeLocations = listOf(PayeeLocation.fixture()),
            categoryGroups = listOf(CategoryGroup.fixture()),
            categories = listOf(Category.fixture()),
            months = listOf(Month.fixture()),
            transactions = listOf(Transaction.summaryFixture()),
            subtransactions = listOf(Subtransaction.fixture()),
            scheduledTransactions = listOf(ScheduledTransaction.fixture()),
            scheduledSubtransactions = listOf(ScheduledSubtransaction.fixture())
        )
        assertEquals(expectedDetail, budgetWithDetail?.budgetDetail)
    }
}
