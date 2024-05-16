package Budget

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

interface BudgetsResource {
    suspend fun getAllBudgets(): List<Budget>
    fun getBudget(budgetId: String)
    fun getSettings(budgetId: String)
}


@Serializable
data class BudgetsResponse(val data: BudgetsData)

@Serializable
data class BudgetsData(val budgets: List<Budget>)

class BudgetsRestResource(
    private val httpClient: HttpClient
) : BudgetsResource {

    override suspend fun getAllBudgets(): List<Budget> {
        val response: BudgetsResponse = httpClient.get("https://api.youneedabudget.com/v1/budgets").body()
        return response.data.budgets
    }

    override fun getBudget(budgetId: String) {
        println("GET /budgets/$budgetId")
    }

    override fun getSettings(budgetId: String) {
        println("GET /budgets/$budgetId/settings")
    }
}