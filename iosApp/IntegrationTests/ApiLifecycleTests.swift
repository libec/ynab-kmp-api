import Shared
import XCTest

class ApiLifecycleTests: XCTestCase {
    func test_loginStartsANewScopeThatIsDiscarded_whenUserLogsOut() {
        let petersToken = "peter_token"
        let stephaniesToken = "stephanie_token"
        let ynabApi = YnabApi()

        ynabApi.login(token: petersToken)
        let petersRepository = ynabApi.getBudgetsRepository()
        ynabApi.logout()

        ynabApi.login(token: stephaniesToken)
        let stephaniesRepository = ynabApi.getBudgetsRepository()

        XCTAssertFalse(petersRepository === stephaniesRepository)
    }

    func test_gettingTheSameRepositoryDuringTheSameSessionReturnsTheSameInstance() {
        let ynabApi = YnabApi.shared
        ynabApi.login(token: "peters_token")
        let repository1 = ynabApi.getBudgetsRepository()
        let repository2 = ynabApi.getBudgetsRepository()
        let repository3 = ynabApi.getBudgetsRepository()

        XCTAssertTrue(repository1 === repository2)
        XCTAssertTrue(repository1 === repository3)
        XCTAssertTrue(repository2 === repository3)
    }
}
