package TestDoubles

import Budget.Budget
import Budget.BudgetsResource

class MockBudgetsResource : BudgetsResource {
    override suspend fun getAllBudgets(): List<Budget> {
        println("Mock GET /budgets")
        val budgetIds = listOf("103", "913", "32104")
        return budgetIds.map { Budget(it) }
    }

    override fun getBudget(budgetId: String) {
        println("Mock GET /budgets/$budgetId")
    }

    override fun getSettings(budgetId: String) {
        println("Mock GET /budgets/$budgetId/settings")
    }
}
