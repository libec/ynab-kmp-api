import Budget.BudgetsRepository
import Budget.BudgetsRepositoryImpl
import Budget.BudgetsResource
import Budget.BudgetsRestResource
import NetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

object LoginScope {}

val budgets = module {
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
