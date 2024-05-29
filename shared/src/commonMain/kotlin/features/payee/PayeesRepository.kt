package features.payee

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface PayeesRepository {
    val payees: StateFlow<List<Payee>>

    suspend fun fetchPayees(budgetId: String)
}

internal class PayeesRepositoryImpl(
    private val resource: PayeesResource
) : PayeesRepository {

    private val mutablePayees = MutableStateFlow(emptyList<Payee>())

    override val payees: StateFlow<List<Payee>>
        get() = mutablePayees.asStateFlow()

    override suspend fun fetchPayees(budgetId: String) {
        resource.getPayees(budgetId).let { payees ->
            mutablePayees.value = payees
        }
    }
}