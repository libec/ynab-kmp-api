package Budget

import NetworkClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface BudgetsResource {
    suspend fun getAllBudgets(): BudgetsResponse
    fun getBudget(budgetId: String)
    fun getSettings(budgetId: String)
}

data class BudgetsResponse(
    val budgets: List<Budget>, val defaultBudget: Budget?
)

object ApiResponse {
    @Serializable
    data class BudgetResponse(val data: BudgetsData)

    @Serializable
    data class BudgetsData(
        val budgets: List<Budget>, @SerialName("default_budget") val defaultBudget: Budget?
    )
}

class BudgetsRestResource(
    private val networkClient: NetworkClient
) : BudgetsResource {

    override suspend fun getAllBudgets(): BudgetsResponse {
        val response: ApiResponse.BudgetResponse = networkClient.get("/budgets")
        return BudgetsResponse(
            budgets = response.data.budgets,
            defaultBudget = response.data.defaultBudget
        )
    }

    override fun getBudget(budgetId: String) {
        println("GET /budgets/$budgetId")
    }

    override fun getSettings(budgetId: String) {
        println("GET /budgets/$budgetId/settings")
    }
}