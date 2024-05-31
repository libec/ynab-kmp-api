import XCTest
import Shared

// Be a good scout and don't run those tests frequently or on CI as they call real backends
// They are off by default as you need to provide your own access token
final class EndToEndTests: XCTestCase {

    func test_itCallsTheApiWithInjectedTokens() async throws {
        // 0 - Replace nil with your token
        // Don't commit your access token to git
        let accessToken: String? = nil

        try XCTSkipIf(accessToken == nil, "You need to enter your access token first")

        // 1 - Inject your access token to start a session
        let ynabSession = YnabSession(userAuthentication: UserAuthentication(token: accessToken!))

        // 2 - Session scoped repositories hold your data for the lifetime of the session
        let budgetRepository = ynabSession.getBudgetsRepository()
        let accountsRepository = ynabSession.getAccountsRepository()
        let payeesRepository = ynabSession.getPayeesRepository()
        let categoriesRepository = ynabSession.getCategoriesRepository()
        let transactionsRepository = ynabSession.getTransactionsRepository()

        // 3 - Fetch budgets
        try await budgetRepository.fetchAllBudgets()

        // 4 - Updates are propagated via StateFlow (AnyPublisher, but on heavy steroids)
        print("💰💰 All your budgets")
        for budget in budgetRepository.budgets.value {
            print("\t\(budget.name): \(budget.id)")
        }

        // 5 - Select the budget you're interested in
        let budget = budgetRepository.budgets.value.randomElement()
        let selectedBudget = try XCTUnwrap(budget)
        print("Selected budget: \(selectedBudget.name)")

        // 6 - Load accounts
        try await accountsRepository.fetchAccounts(budgetId: selectedBudget.id)
        print("💳💳 All your open accounts")
        for account in accountsRepository.accounts.value where !account.closed {
            print("\t\(account.name): \(account.balance)")
        }

        // 7 - Load payees
        try await payeesRepository.fetchPayees(budgetId: selectedBudget.id)
        print("🧞‍♂️🧞‍♂️ All your payees")
        for payee in payeesRepository.payees.value {
            print("\t\(payee.name)")
        }

        // 8 - Load categories
        print("✉️✉️ All your categories")
        try await categoriesRepository.fetchCategories(budgetId: selectedBudget.id)
        for category in categoriesRepository.categories.value {
            print("\t\(category.name)")
        }

        // 9 - Load transactions
        try await transactionsRepository.fetchTransactions(budgetId: selectedBudget.id)
        print("🔁🔁 All your transactions")
        for transaction in transactionsRepository.transactions.value {
            print("\t\(transaction.payeeName ?? ""): \(transaction.amount)")
        }

        print("That's it. Kotlin code compiled into LLVM running on iOS. How cool is that!?")
    }
}
