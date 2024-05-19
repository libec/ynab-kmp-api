import XCTest
import Shared

final class IntegrationTests: XCTestCase {
    var ynabApi: YnabApi!

    override func setUp() {
        super.setUp()
        ynabApi = YnabApi()

        let token = loadAccessToken()!
        ynabApi.login(token: token)
    }

    override func tearDown() {
        super.tearDown()
        ynabApi.logout()
        ynabApi = nil
    }

    func loadAccessToken() -> String? {
        guard let url = Bundle(for: Self.self).url(forResource: "token", withExtension: "txt") else {
            return nil
        }

        guard let token = try? String(contentsOf: url).replacingOccurrences(of: "\n", with: "") else {
            return nil
        }

        return token
    }

    func test_itCallsTheApiWithInjectedTokens() async {
        let repository = ynabApi.getBudgetsRepository()
        try? await repository.fetchAllBudgets()
        let expectation = expectation(description: "wait for the budgets to load")
        Task.detached {
            for await budgets in repository.budgets {
                print("All budgets")
                for budget in budgets {
                    print("💰💰: \(budget)")
                }
                expectation.fulfill()
            }
        }

        await fulfillment(of: [expectation])
    }
}
