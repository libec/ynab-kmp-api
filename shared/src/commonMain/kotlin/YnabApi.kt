import Budget.BudgetsRepository
import Budget.BudgetsRepositoryImpl
import Budget.BudgetsResource
import Budget.BudgetsRestResource
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.scope.Scope
import org.koin.dsl.module

object LoginScope { }

val budgets = module {
    scope<LoginScope> {
        scoped<BudgetsRepository> { BudgetsRepositoryImpl(resource = get()) }
        factory<BudgetsResource> { BudgetsRestResource() }
    }
}

data class Session(val token: String)

object YnabApi: KoinComponent {
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
