import XCTest
import Shared

class IntegrationTestCase: XCTestCase {
    var ynabSession: YnabSession!

    override func setUp() {
        super.setUp()

        let token = loadAccessToken()!
        ynabSession = YnabSession(userAuthentication: UserAuthentication(token: token))
    }

    override func tearDown() {
        super.tearDown()
        ynabSession.logout()
    }

    private func loadAccessToken() -> String? {
        guard let url = Bundle(for: Self.self).url(forResource: "token", withExtension: "txt") else {
            return nil
        }

        guard let token = try? String(contentsOf: url).replacingOccurrences(of: "\n", with: "") else {
            return nil
        }

        return token
    }

}
