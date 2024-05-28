package features.budget

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface BudgetsRepository {
    val budgets: StateFlow<List<Budget>>
    val selectedBudget: StateFlow<Budget?>

    suspend fun fetchAllBudgets()
    suspend fun fetchBudgetWithDetail(budgetId: String): Budget
}

internal class BudgetsRepositoryImpl(
    private val resource: BudgetsResource
) : BudgetsRepository {

    private val mutableBudgets = MutableStateFlow(listOf<Budget>())
    private val mutableSelectedBudgets = MutableStateFlow<Budget?>(null)

    override val budgets: StateFlow<List<Budget>>
        get() = mutableBudgets.asStateFlow()

    override val selectedBudget: StateFlow<Budget?>
        get() = mutableSelectedBudgets.asStateFlow()

    override suspend fun fetchAllBudgets() {
        val response = resource.getAllBudgets()
        mutableBudgets.value = response.budgets
        mutableSelectedBudgets.value = response.defaultBudget
    }

    override suspend fun fetchBudgetWithDetail(budgetId: String): Budget {
        val budget = resource.getBudget(budgetId)
        val budgets = mutableBudgets.value.toMutableList()
        val index = budgets.indexOfFirst { it.id == budgetId }
        if (index == -1) {
            budgets.add(budget)
        } else {
            budgets[index] = budget
        }
        return budget
    }
}
