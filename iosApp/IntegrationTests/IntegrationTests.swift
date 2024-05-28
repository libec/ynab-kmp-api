import XCTest
import Shared

final class IntegrationTests: IntegrationTestCase {

    func test_itCallsTheApiWithInjectedTokens() async throws {
        let repository = ynabSession.getBudgetsRepository()
        try await repository.fetchAllBudgets()
        print("All budgets:")
        for budget in repository.budgets.value {
            budget.prettyPrint()
        }

        for budget in repository.budgets.value {
            let detail = try await repository.fetchBudgetWithDetail(budgetId: budget.id)
            print(detail.budgetDetail?.accounts)
            print(detail.budgetDetail?.transactions)
        }
    }
}

extension Budget {
    func prettyPrint() {
        print("Budget: \(name) | ID: \(id) ")
        for account in (budgetDetail?.accounts ?? []) where !account.closed {
            account.prettyPrint()
        }
        print("\n")
    }
}

extension Account {
    func prettyPrint() {
        print("Account: \(name) with balance of \(balance)")
    }
}
