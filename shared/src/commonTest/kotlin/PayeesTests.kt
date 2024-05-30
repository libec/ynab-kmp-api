import features.payee.Payee
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class PayeesTests: MockedResponseTests() {

    @Test
    fun `it downloads and stores payees`() = runBlocking {
        val mockedResponses = listOf(Pair("budgets/$budgetId/payees", "payees.json"))
        val ynabSession = makeYnabSession(mockedResponses)
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