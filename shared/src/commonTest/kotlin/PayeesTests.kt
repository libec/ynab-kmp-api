import features.budget.Budget
import features.payee.Payee
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import mocks.NetworkClientMockFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class PayeesTests {

    @Test
    fun `it downloads and stores payees`() = runBlocking {
        val budgetId = Budget.fixture().id
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            listOf(Pair("budgets/$budgetId/payees", "payees.json"))
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val payeesRepository = ynabSession.getPayeesRepository()

        payeesRepository.fetchPayees(budgetId)

        val expectedPayees = listOf(
            Payee.fixture(),
            Payee.fixture(
                id = "payee-id-2102f",
                name = "Spotify",
                deleted = true
            ),
            Payee.fixture(
                id = "ynaid-yesyoudo",
                name = "YNAB"
            )
        )

        assertEquals(expectedPayees, payeesRepository.payees.value)
    }
}