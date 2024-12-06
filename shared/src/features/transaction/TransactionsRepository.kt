package features.transaction

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface TransactionsRepository {
    val transactions: StateFlow<List<Transaction>>

    suspend fun fetchTransactions(budgetId: String)
}

internal class TransactionsRepositoryImpl(
    private val resource: TransactionsResource
) : TransactionsRepository {

    private val mutableTransactions = MutableStateFlow(emptyList<Transaction>())

    override val transactions: StateFlow<List<Transaction>>
        get() = mutableTransactions.asStateFlow()

    override suspend fun fetchTransactions(budgetId: String) {
        resource.getTransactions(budgetId).let { transactions ->
            mutableTransactions.value = transactions
        }
    }
}