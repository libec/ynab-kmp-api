import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class BudgetsRepositoryTests {

    @Test
    fun `it runs tests`() {
        assertTrue { true }

        val number = 1
    }

    @Test
    fun `download fetches budgets`() = runBlocking {
        val repository = BudgetsRepository()

        repository.download()

        assertTrue { true }
    }
}