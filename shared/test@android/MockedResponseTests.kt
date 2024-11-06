import features.budget.Budget
import fixtures.fixture
import mocks.HttpClientMockFactory
import session.YnabSession
import session.UserAuthentication

open class MockedResponseTests {
    protected val budgetId = Budget.fixture().id

    fun makeYnabSession(mockedResponses: List<Pair<String, String>>): YnabSession {
        val mockedHttpClient =
            HttpClientMockFactory().makeMockedHttpClient(mockedResponses)
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("dummy_token"))
        ynabSession.loginScope.declare(mockedHttpClient)
        return ynabSession
    }
}
