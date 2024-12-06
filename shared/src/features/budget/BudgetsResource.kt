package features.budget

import features.account.Account
import features.category.Category
import features.category.CategoryGroup
import features.month.Month
import features.payee.Payee
import features.payee.PayeeLocation
import features.transaction.ScheduledSubtransaction
import features.transaction.ScheduledTransaction
import features.transaction.Subtransaction
import features.transaction.Transaction
import infrastructure.formats.CurrencyFormat
import infrastructure.formats.DateFormat
import infrastructure.networking.NetworkClient
import kotlinx.serialization.Serializable

internal interface BudgetsResource {
    suspend fun getAllBudgets(): BudgetsResponse
    suspend fun getBudget(budgetId: String): Budget
}

internal data class BudgetsResponse(
    val budgets: List<Budget>,
    val defaultBudget: Budget?
)

internal class BudgetsRestResource(
    private val networkClient: NetworkClient
) : BudgetsResource {

    override suspend fun getAllBudgets(): BudgetsResponse {
        val response: ApiResponse.BudgetResponse =
            networkClient.get("/budgets", listOf(Pair("include_accounts", "true")))
        return BudgetsResponse(
            budgets = response.data.budgets.map { it.toBudget() },
            defaultBudget = response.data.defaultBudget?.toBudget()
        )
    }

    override suspend fun getBudget(budgetId: String): Budget {
        val response: ApiResponse.BudgetDetailResponse =
            networkClient.get("/budgets/$budgetId")

        return response.data.budget.toBudget()
    }
}

private fun ApiResponse.BudgetSummary.toBudget(): Budget {
    return Budget(
        id = this.id,
        name = this.name,
        lastModifiedOn = this.lastModifiedOn,
        firstMonth = this.firstMonth,
        lastMonth = this.lastMonth,
        dateFormat = this.dateFormat,
        currencyFormat = this.currencyFormat,
        budgetDetail = Budget.BudgetDetail(accounts = this.accounts)
    )
}

private fun ApiResponse.BudgetDetail.toBudget(): Budget {
    return Budget(
        id = this.id,
        name = this.name,
        lastModifiedOn = this.lastModifiedOn,
        firstMonth = this.firstMonth,
        lastMonth = this.lastMonth,
        dateFormat = this.dateFormat,
        currencyFormat = this.currencyFormat,
        budgetDetail = Budget.BudgetDetail(
            accounts = this.accounts,
            payees = this.payees,
            payeeLocations = this.payeeLocations,
            categoryGroups = this.categoryGroups,
            categories = this.categories,
            months = this.months,
            transactions = this.transactions,
            subtransactions = this.subtransactions,
            scheduledTransactions = this.scheduledTransactions,
            scheduledSubtransactions = this.scheduledSubtransactions
        )
    )
}

private object ApiResponse {
    @Serializable data class BudgetResponse(val data: BudgetsData)
    @Serializable data class BudgetsData(val budgets: List<BudgetSummary>, val defaultBudget: BudgetSummary?)
    @Serializable data class BudgetDetailResponse(val data: BudgetsDetailData)
    @Serializable data class BudgetsDetailData(val budget: BudgetDetail)

    @Serializable
    data class BudgetSummary(
        val id: String,
        val name: String,
        val lastModifiedOn: String,
        val firstMonth: String,
        val lastMonth: String,
        val dateFormat: DateFormat,
        val currencyFormat: CurrencyFormat,
        val accounts: List<Account>
    )

    @Serializable
    data class BudgetDetail(
        val id: String,
        val name: String,
        val lastModifiedOn: String,
        val firstMonth: String,
        val lastMonth: String,
        val dateFormat: DateFormat,
        val currencyFormat: CurrencyFormat,
        val accounts: List<Account>,
        val payees: List<Payee>,
        val payeeLocations: List<PayeeLocation>,
        val categoryGroups: List<CategoryGroup>,
        val categories: List<Category>,
        val months: List<Month>,
        val transactions: List<Transaction>,
        val subtransactions: List<Subtransaction>,
        val scheduledTransactions: List<ScheduledTransaction>,
        val scheduledSubtransactions: List<ScheduledSubtransaction>
    )
}
