import Budget.BudgetsRepository
import Budget.BudgetsRepositoryImpl
import Budget.BudgetsResource
import Budget.BudgetsRestResource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.scope.Scope
import org.koin.dsl.module

object Scopes : KoinComponent {

    object LoginScope {}

    val ynabModules = module {
        scope<LoginScope> {
            scoped<BudgetsRepository> { BudgetsRepositoryImpl(get()) }
            factory<BudgetsResource> { BudgetsRestResource(get()) }
            factory<NetworkClient> { NetworkClient(get(), get()) }
            factory<HttpClient> {
                HttpClient {
                    install(ContentNegotiation) {
                        json(Json {
                            ignoreUnknownKeys = true
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