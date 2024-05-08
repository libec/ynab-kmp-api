import XCTest
import Shared

final class IntegrationTests: XCTestCase {
    var ynabApi: YnabApi!

    override func setUp() {
        super.setUp()
        ynabApi = YnabApi()
        ynabApi.login(token: "random")
    }

    override func tearDown() {
        super.tearDown()
        ynabApi.logout()
        ynabApi = nil
    }

    func test_itCallsTheApiWithInjectedTokens() async {
        let repository = ynabApi.getBudgetsRepository()
        Task.detached {
            for await budgets in repository.budgets {
                print("All budgets")
                for budget in budgets {
                    print(budget.id)
                }
            }
        }

        repository.add(budget: Budget(id: "budg1"))
        repository.add(budget: Budget(id: "budg12"))
    }

    func test_itCanAwaitKotlinSuspendingFunction() async {
        let repository = ynabApi.getBudgetsRepository()

        try? await repository.fetchAllBudgets()
    }
}
