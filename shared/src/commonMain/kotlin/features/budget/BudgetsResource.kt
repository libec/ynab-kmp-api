package features.budget

import infrastructure.networking.NetworkClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal interface BudgetsResource {
    suspend fun getAllBudgets(): BudgetsResponse
    fun getBudget(budgetId: String)
}

internal data class BudgetsResponse(
    val budgets: List<Budget>,
    val defaultBudget: Budget?
)

internal class BudgetsRestResource(
    private val networkClient: NetworkClient
) : BudgetsResource {

    object ApiResponse {
        @Serializable
        data class BudgetResponse(val data: BudgetsData)

        @Serializable
        data class BudgetsData(
            val budgets: List<Budget>,
            @SerialName("default_budget")
            val defaultBudget: Budget?
        )
    }

    override suspend fun getAllBudgets(): BudgetsResponse {
        val response: ApiResponse.BudgetResponse =
            networkClient.get("/budgets", listOf(Pair("include_accounts", "true")))
        return BudgetsResponse(
            budgets = response.data.budgets,
            defaultBudget = response.data.defaultBudget
        )
    }

    override fun getBudget(budgetId: String) {
        println("GET /budgets/$budgetId")
    }
}