import kotlinx.coroutines.runBlocking
import kotlin.test.Test

// Be a good scout and don't run those tests frequently or on CI as they call real backends
// They are off by default as you need to provide your own access token
class EndToEndTests {

    @Test
    fun `it calls the API with injected tokens`() = runBlocking {
        // 0 - Replace null with your token
        // Don't commit your access token to git
        val accessToken: String? = null

        if (accessToken == null) {
            println("You need to provide your access token to run this test")
            return@runBlocking
        }

        // 1 - Inject your access token to start a session
        val ynabSession = YnabSession(UserAuthentication(accessToken))

        // 2 - Session scoped repositories hold your data for the lifetime of the session
        val budgetRepository = ynabSession.getBudgetsRepository()
        val accountsRepository = ynabSession.getAccountsRepository()
        val payeesRepository = ynabSession.getPayeesRepository()
        val categoriesRepository = ynabSession.getCategoriesRepository()
        val transactionsRepository = ynabSession.getTransactionsRepository()

        // 3 - Fetch budgets
        budgetRepository.fetchAllBudgets()

        // 4 - Updates are propagated via StateFlow
        println("💰💰 All your budgets")
        for (budget in budgetRepository.budgets.value) {
            println("\t${budget.name}: ${budget.id}")
        }

        // 5 - Select the budget you're interested in
        val budget = budgetRepository.budgets.value.random()
        println("Selected budget: ${budget.name}")

        // 6 - Load accounts
        accountsRepository.fetchAccounts(budgetId = budget.id)
        println("💳💳 All your open accounts")
        for (account in accountsRepository.accounts.value.filter { !it.closed }) {
            println("\t${account.name}: ${account.balance}")
        }

        // 7 - Load payees
        payeesRepository.fetchPayees(budgetId = budget.id)
        println("🧞‍🧞️ All your payees")
        for (payee in payeesRepository.payees.value) {
            println("\t${payee.name}")
        }

        // 8 - Load categories
        println("✉️✉️ All your categories")
        categoriesRepository.fetchCategories(budgetId = budget.id)
        for (category in categoriesRepository.categories.value) {
            println("\t${category.name}")
        }

        // 9 - Load transactions
        transactionsRepository.fetchTransactions(budgetId = budget.id)
        println("🔁🔁 All your transactions")
        for (transaction in transactionsRepository.transactions.value) {
            println("\t${transaction.payeeName}: ${transaction.amount}")
        }

        println("That's it. Pretty cool, but check out those same end to end tests on iOS")
    }
}