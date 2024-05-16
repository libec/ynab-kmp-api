import Budget.BudgetsRepository
import Budget.BudgetsRepositoryImpl
import Budget.BudgetsResource
import Budget.BudgetsRestResource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

object LoginScope {}

val budgets = module {
    scope<LoginScope> {
        scoped<BudgetsRepository> { BudgetsRepositoryImpl(resource = get()) }
        factory<BudgetsResource> { BudgetsRestResource(get()) }
        factory<HttpClient> {
            HttpClient {
                install(ContentNegotiation) {
                    json()
                }
            }
        }
    }
}
