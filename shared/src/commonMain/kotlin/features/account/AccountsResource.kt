package features.account

import infrastructure.networking.NetworkClient
import kotlinx.serialization.Serializable

internal interface AccountsResource {
    suspend fun getAccounts(budgetId: String): List<Account>
}

internal class AccountsRestResource(
    private val networkClient: NetworkClient
) : AccountsResource {

    override suspend fun getAccounts(budgetId: String): List<Account> {
        val response: features.account.ApiResponse.AccountsResponse =
            networkClient.get("/budgets/$budgetId/accounts")
        return response.data.accounts
    }
}

private object ApiResponse {
    @Serializable
    data class AccountsResponse(val data: Accounts)

    @Serializable
    data class Accounts(
        val accounts: List<Account>,
    )
}