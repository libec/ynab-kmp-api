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
import features.payee.PayeesRepository
import features.payee.PayeesRepositoryImpl
import features.payee.PayeesResource
import features.payee.PayeesRestResource
import infrastructure.networking.NetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
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

            factory<NetworkClient> { NetworkClient(get(), get()) }
            factory<HttpClient> {
                HttpClient {
                    install(ContentNegotiation) {
                        json(Json {
                            ignoreUnknownKeys = true
                            namingStrategy = JsonNamingStrategy.SnakeCase
                            explicitNulls = false
                        })
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