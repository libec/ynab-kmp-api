package Budget

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface BudgetsRepository {
    val budgets: StateFlow<List<Budget>>

    suspend fun fetchAllBudgets()
    fun add(budget: Budget)
}

class BudgetsRepositoryImpl(
    private val resource: BudgetsResource
) : BudgetsRepository {

    private val mutableBudgets = MutableStateFlow(listOf<Budget>())

    override val budgets: StateFlow<List<Budget>>
        get() = mutableBudgets.asStateFlow()

    override suspend fun fetchAllBudgets() {
        val budgets = resource.getAllBudgets()
        mutableBudgets.value = budgets
    }

    override fun add(budget: Budget) {
        mutableBudgets.value = mutableBudgets.value + budget
    }
}
