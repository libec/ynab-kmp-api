import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class YnabApiTests {
    @Test
    fun `login starts a new scope that is discarded when user logs out`() {
        val petersToken = "peter_token"
        val stephaniesToken = "stephanie_token"

        YnabApi.login(petersToken)
        val petersRepository = YnabApi.getBudgetsRepository()
        YnabApi.logout()
        YnabApi.login(stephaniesToken)
        val stephaniesRepository = YnabApi.getBudgetsRepository()

        assertNotEquals(petersRepository, stephaniesRepository)
    }

    @Test
    fun `getting the same repository during the same session returns the same instance`() {
        val petersToken = "peter_token"
        YnabApi.login(petersToken)

        val repository1 = YnabApi.getBudgetsRepository()
        val repository2 = YnabApi.getBudgetsRepository()
        val repository3 = YnabApi.getBudgetsRepository()

        assertEquals(repository1, repository2)
        assertEquals(repository2, repository3)
        assertEquals(repository1, repository3)
    }
}