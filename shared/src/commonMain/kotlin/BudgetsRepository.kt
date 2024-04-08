import kotlinx.coroutines.delay

class BudgetsRepository {

    suspend fun download(): String {
        delay(500)
        return "Oh yeah"
    }

    fun greet(): String {
        return "Hello budgets!"
    }
}
