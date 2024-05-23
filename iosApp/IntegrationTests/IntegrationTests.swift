import XCTest
import Shared

final class IntegrationTests: IntegrationTestCase {

    func test_itCallsTheApiWithInjectedTokens() async {
        let repository = ynabSession.getBudgetsRepository()
        try? await repository.fetchAllBudgets()
        print("All budgets")
        for budget in repository.budgets.value {
            print("💸💸: \(budget)")
        }
    }
}
