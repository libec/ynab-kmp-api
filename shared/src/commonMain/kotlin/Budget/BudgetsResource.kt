package Budget

interface BudgetsResource {
    fun getAllBudgets(): List<Budget>
    fun getBudget(budgetId: String)
    fun getSettings(budgetId: String)
}

class MockBudgetsResource: BudgetsResource {
    override fun getAllBudgets(): List<Budget> {
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

class BudgetsRestResource: BudgetsResource {
    override fun getAllBudgets(): List<Budget> {
        println("GET /budgets")
        val budgetIds = listOf("103", "32104")
        return budgetIds.map { Budget(it) }
    }

    override fun getBudget(budgetId: String) {
        println("GET /budgets/$budgetId")
    }

    override fun getSettings(budgetId: String) {
        println("GET /budgets/$budgetId/settings")
    }
}