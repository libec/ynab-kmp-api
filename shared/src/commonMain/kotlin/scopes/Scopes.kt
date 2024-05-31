package scopes

import UserAuthentication
import features.account.AccountsRepository
import features.account.AccountsRepositoryImpl
import features.account.AccountsResource
import features.account.AccountsRestResource
import features.budget.BudgetsRepository
import features.budget.BudgetsRepositoryImpl
import features.budget.BudgetsResource
import features.budget.BudgetsRestResource
import features.category.CategoriesRepository
import features.category.CategoriesRepositoryImpl
import features.category.CategoriesResource
import features.category.CategoriesRestResource
import features.payee.PayeesRepository
import features.payee.PayeesRepositoryImpl
import features.payee.PayeesResource
import features.payee.PayeesRestResource
import features.transaction.TransactionsRepository
import features.transaction.TransactionsRepositoryImpl
import features.transaction.TransactionsResource
import features.transaction.TransactionsRestResource
import infrastructure.networking.NetworkClient
import infrastructure.networking.makeJsonNegotiation
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal object Scopes : KoinComponent {

    object LoginScope {}

    val ynabModules = module {
        scope<LoginScope> {
            scoped<BudgetsRepository> { BudgetsRepositoryImpl(get()) }
            factory<BudgetsResource> { BudgetsRestResource(get()) }
            scoped<AccountsRepository> { AccountsRepositoryImpl(get()) }
            factory<AccountsResource> { AccountsRestResource(get()) }
            scoped<PayeesRepository> { PayeesRepositoryImpl(get()) }
            factory<PayeesResource> { PayeesRestResource(get()) }
            scoped<CategoriesRepository> { CategoriesRepositoryImpl(get()) }
            factory<CategoriesResource> { CategoriesRestResource(get()) }
            scoped<TransactionsRepository> { TransactionsRepositoryImpl(get()) }
            factory<TransactionsResource> { TransactionsRestResource(get()) }

            factory<NetworkClient> { NetworkClient(get(), get()) }
            factory<HttpClient> {
                // TODO: - Moving json plugin installation to NetworkClient.kt doesn't
                // seem to take effect on iOS as it fails on parsing. Investigate why.
                HttpClient {
                    install(ContentNegotiation) {
                        json(makeJsonNegotiation())
                    }
                }
            }
        }
    }

    init {
        startKoin { modules(ynabModules) }
    }

    fun newLoginScope(userAuthentication: UserAuthentication): Scope {
        val scope = getKoin().createScope<LoginScope>()
        scope.declare(userAuthentication)
        return scope
    }
}