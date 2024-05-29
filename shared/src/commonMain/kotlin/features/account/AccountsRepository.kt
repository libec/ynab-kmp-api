package features.account

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface AccountsRepository {
    val accounts: StateFlow<List<Account>>

    suspend fun fetchAccounts(budgetId: String)
}

internal class AccountsRepositoryImpl(
    private val resource: AccountsResource
) : AccountsRepository {

    private val mutableAccounts = MutableStateFlow(emptyList<Account>())

    override val accounts: StateFlow<List<Account>>
        get() = mutableAccounts.asStateFlow()

    override suspend fun fetchAccounts(budgetId: String) {
        val accounts = resource.getAccounts(budgetId)
        mutableAccounts.value = accounts
    }
}