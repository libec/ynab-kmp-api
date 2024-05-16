import Budget.BudgetsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.scope.Scope

/*
 TODO:
  - Make YnabApi a class,
  - figure out smarter way to start and stop scopes,
  - and make it less koin dependent
 */
object YnabApi : KoinComponent {
    internal var loginScope: Scope? = null

    init {
        startKoin { modules(budgets) }
    }

    fun login(token: String) {
        getKoin().createScope<LoginScope>()
        loginScope = getKoin().createScope<LoginScope>()
        val session = Session(token)
        loginScope?.declare(session)
    }

    fun logout() {
        loginScope?.close()
        loginScope = null
    }

    fun getBudgetsRepository(): BudgetsRepository {
        return loginScope?.get() ?: throw IllegalStateException("User is not logged in")
    }
}
