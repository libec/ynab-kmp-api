import shared
import XCTest

class ApiLifecycleTests: XCTestCase {
    func test_loginStartsANewScopeThatIsDiscarded_whenUserLogsOut() {
        let petersToken = "peter_token"
        let stephaniesToken = "stephanie_token"
        let petersSession = YnabSession(userAuthentication: UserAuthentication(token: petersToken))
        let stephaniesSession = YnabSession(userAuthentication: UserAuthentication(token: stephaniesToken))
        let petersRepository = petersSession.getBudgetsRepository()
        let stephaniesRepository = stephaniesSession.getBudgetsRepository()

        XCTAssertFalse(petersRepository === stephaniesRepository)
    }

    func test_gettingTheSameRepositoryDuringTheSameSessionReturnsTheSameInstance() {
        let petersToken = "peter_token"
        let petersSession = YnabSession(userAuthentication: UserAuthentication(token: petersToken))
        let repository1 = petersSession.getBudgetsRepository()
        let repository2 = petersSession.getBudgetsRepository()
        let repository3 = petersSession.getBudgetsRepository()

        XCTAssertTrue(repository1 === repository2)
        XCTAssertTrue(repository1 === repository3)
        XCTAssertTrue(repository2 === repository3)
    }
}
