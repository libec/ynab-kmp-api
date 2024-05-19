package Budget

import Session
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface BudgetsResource {

    suspend fun getAllBudgets(): BudgetsResponse
    fun getBudget(budgetId: String)
    fun getSettings(budgetId: String)
}

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

data class BudgetsResponse(
    val budgets: List<Budget>,
    val defaultBudget: Budget?
)

class BudgetsRestResource(
    private val httpClient: HttpClient,
    private val session: Session
) : BudgetsResource {

    override suspend fun getAllBudgets(): BudgetsResponse {
        val response: ApiResponse.BudgetResponse = httpClient.get("https://api.youneedabudget.com/v1/budgets") {
            header(HttpHeaders.Authorization, "Bearer ${session.token}")
        }.body()
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