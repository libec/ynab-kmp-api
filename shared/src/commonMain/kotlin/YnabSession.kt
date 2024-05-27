import features.budget.BudgetsRepository
import org.koin.core.scope.Scope
import scopes.Scopes

class YnabSession(
    private val userAuthentication: UserAuthentication,
) {
    internal var loginScope: Scope

    init {
        loginScope = Scopes.newLoginScope(userAuthentication)
    }

    fun getBudgetsRepository(): BudgetsRepository {
        return loginScope?.get() ?: throw IllegalStateException("User is not logged in")
    }

    fun logout() {
        loginScope.close()
    }
}
