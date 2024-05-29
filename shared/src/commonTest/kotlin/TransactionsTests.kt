import features.budget.Budget
import features.transaction.Transaction
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import mocks.NetworkClientMockFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionsTests {

    @Test
    fun `it downloads and stores transactions`() = runBlocking {
        val budgetId = Budget.fixture().id
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            listOf(Pair("budgets/$budgetId/transactions", "transactions.json"))
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val transactionsRepository = ynabSession.getTransactionsRepository()

        transactionsRepository.fetchTransactions(budgetId)

        val expectedTransactions = listOf(
            Transaction.fixture(),
            Transaction.fixture(
                id = "zkea0-1",
                amount = 800,
                memo = "Food for laters",
                accountName = "Revolut account",
                payeeName = "Health Store",
                categoryName = "Health",
                subtransactions = emptyList(),
            ),
            Transaction.fixture(
                id = "tv-transa",
                amount = 8000,
                memo = "to bingewatch pawpatrol with the kids",
                accountName = "Savings account",
                payeeName = "Electronics Store",
                categoryName = "Home Equipment",
                subtransactions = emptyList(),
            )
        )

        assertEquals(expectedTransactions, transactionsRepository.transactions.value)
    }
}