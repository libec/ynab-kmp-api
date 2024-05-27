package features.budget

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface BudgetsRepository {
    val budgets: StateFlow<List<Budget>>
    val selectedBudget: StateFlow<Budget?>

    suspend fun fetchAllBudgets()
    fun add(budget: Budget)
}

internal class BudgetsRepositoryImpl(
    private val resource: BudgetsResource
) : BudgetsRepository {

    private val mutableBudgets = MutableStateFlow(listOf<Budget>())
    private val mutableSelectedBudgets = MutableStateFlow<Budget?>(null)

    override val budgets: StateFlow<List<Budget>>
        get() = mutableBudgets.asStateFlow()

    override  val selectedBudget: StateFlow<Budget?>
        get() = mutableSelectedBudgets.asStateFlow()

    override suspend fun fetchAllBudgets() {
        val response = resource.getAllBudgets()
        mutableBudgets.value = response.budgets
        mutableSelectedBudgets.value = response.defaultBudget
    }

    override fun add(budget: Budget) {
        mutableBudgets.value = mutableBudgets.value + budget
    }
}
