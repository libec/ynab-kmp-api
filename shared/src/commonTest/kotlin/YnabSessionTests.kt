import kotlinx.coroutines.runBlocking
import mocks.HttpClientMockFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class YnabSessionTests {

    @Test
    fun `each ynab session starts a scope with its own scoped instances`() {
        val petersToken = "peter_token"
        val stephaniesToken = "stephanie_token"

        val petersSession = YnabSession(UserAuthentication(petersToken))
        val stephaniesSession = YnabSession(UserAuthentication(stephaniesToken))
        val petersRepository = petersSession.getBudgetsRepository()
        val stephaniesRepository = stephaniesSession.getBudgetsRepository()

        assertNotEquals(petersRepository, stephaniesRepository)
    }

    @Test
    fun `getting the same repository during the same session returns the same instance`() {
        val petersToken = "peter_token"
        val petersSession = YnabSession(UserAuthentication(petersToken))

        val repository1 = petersSession.getBudgetsRepository()
        val repository2 = petersSession.getBudgetsRepository()
        val repository3 = petersSession.getBudgetsRepository()

        assertEquals(repository1, repository2)
        assertEquals(repository2, repository3)
        assertEquals(repository1, repository3)
    }

    @Test
    fun `network client uses injected access token`() = runBlocking {
        val petersToken = "peters-token"
        val mockedHttpClient = HttpClientMockFactory().makeMockedHttpClient(
            accessToken = petersToken,
            mockedResponse = "budgets.json"
        )
        val ynabSession = YnabSession(UserAuthentication(petersToken))
        ynabSession.loginScope.declare(mockedHttpClient)

        val budgetsRepository = ynabSession.getBudgetsRepository()
        budgetsRepository.fetchAllBudgets()

        assertEquals(budgetsRepository.budgets.value.size, 2)
    }
}