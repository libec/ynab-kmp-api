package features.payee

import infrastructure.networking.NetworkClient
import kotlinx.serialization.Serializable

internal interface PayeesResource {
    suspend fun getPayees(budgetId: String): List<Payee>
}

internal class PayeesRestResource(
    private val networkClient: NetworkClient
) : PayeesResource {

    override suspend fun getPayees(budgetId: String): List<Payee> {
        val response: ApiResponse.PayeesResponse =
            networkClient.get("/budgets/$budgetId/payees")
        return response.data.payees
    }
}

private object ApiResponse {
    @Serializable
    data class PayeesResponse(val data: Payees)

    @Serializable
    data class Payees(
        val payees: List<Payee>
    )
}