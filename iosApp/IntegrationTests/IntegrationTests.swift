import XCTest
import Shared

final class IntegrationTests: IntegrationTestCase {

    func test_itCallsTheApiWithInjectedTokens() async throws {
        let budgetRepository = ynabSession.getBudgetsRepository()
        let accountsRepository = ynabSession.getAccountsRepository()

        try await budgetRepository.fetchAllBudgets()
        print("All budgets:")
        for budget in budgetRepository.budgets.value {
            budget.prettyPrint()
        }

        for account in accountsRepository.accounts.value where !account.closed {
            account.prettyPrint()
        }
    }
}

extension Budget {
    func prettyPrint() {
        print("Budget: \(name) | ID: \(id) ")
    }
}

extension Account {
    func prettyPrint() {
        print("Account: \(name) with balance of \(balance)")
    }
}
