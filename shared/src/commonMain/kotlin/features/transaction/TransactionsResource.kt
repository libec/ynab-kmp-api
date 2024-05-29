package features.transaction

import infrastructure.networking.NetworkClient
import kotlinx.serialization.Serializable

internal interface TransactionsResource {
    suspend fun getTransactions(budgetId: String): List<Transaction>
}

internal class TransactionsRestResource(
    private val networkClient: NetworkClient
) : TransactionsResource {

    override suspend fun getTransactions(budgetId: String): List<Transaction> {
        val response: ApiResponse.TransactionsResponse =
            networkClient.get("/budgets/$budgetId/transactions")
        return response.data.transactions
    }
}

private object ApiResponse {
    @Serializable
    data class TransactionsResponse(val data: Transactions)

    @Serializable
    data class Transactions(
        val transactions: List<Transaction>,
    )
}