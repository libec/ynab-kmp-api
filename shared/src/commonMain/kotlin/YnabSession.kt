import features.account.AccountsRepository
import features.budget.BudgetsRepository
import features.category.CategoriesRepository
import features.payee.PayeesRepository
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
        return loginScope.get()
    }

    fun getAccountsRepository(): AccountsRepository {
        return loginScope.get()
    }

    fun getPayeesRepository(): PayeesRepository {
        return loginScope.get()
    }

    fun getCategoriesRepository(): CategoriesRepository {
        return loginScope.get()
    }

    fun logout() {
        loginScope.close()
    }
}
