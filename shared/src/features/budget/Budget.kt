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
import kotlinx.serialization.Serializable

@Serializable
data class Budget(
    val id: String,
    val name: String,
    val lastModifiedOn: String,
    val firstMonth: String,
    val lastMonth: String,
    val dateFormat: DateFormat,
    val currencyFormat: CurrencyFormat,
    val budgetDetail: BudgetDetail? = null
) {
    @Serializable
    data class BudgetDetail(
        val accounts: List<Account> = emptyList(),
        val payees: List<Payee> = emptyList(),
        val payeeLocations: List<PayeeLocation> = emptyList(),
        val categoryGroups: List<CategoryGroup> = emptyList(),
        val categories: List<Category> = emptyList(),
        val months: List<Month> = emptyList(),
        val transactions: List<Transaction> = emptyList(),
        val subtransactions: List<Subtransaction> = emptyList(),
        val scheduledTransactions: List<ScheduledTransaction> = emptyList(),
        val scheduledSubtransactions: List<ScheduledSubtransaction> = emptyList()
    )
}
