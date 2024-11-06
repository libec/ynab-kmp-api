package fixtures

import features.account.Account
import features.budget.Budget
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

fun Budget.Companion.fixture(
    id: String = "budg-id-dbaw4rj",
    name: String = "Personal Budget",
    lastModifiedOn: String = "2024-05-18T09:54:28.054Z",
    firstMonth: String = "2024-05-01",
    lastMonth: String = "2024-05-31",
    dateFormat: DateFormat = DateFormat.fixture(),
    currencyFormat: CurrencyFormat = CurrencyFormat.fixture(),
    budgetDetail: Budget.BudgetDetail = Budget.BudgetDetail.fixture()
): Budget {
    return Budget(
        id = id,
        name = name,
        lastModifiedOn = lastModifiedOn,
        firstMonth = firstMonth,
        lastMonth = lastMonth,
        dateFormat = dateFormat,
        currencyFormat = currencyFormat,
        budgetDetail = budgetDetail
    )
}

fun Budget.BudgetDetail.Companion.fixture(
    accounts: List<Account> = emptyList(),
    payees: List<Payee> = emptyList(),
    payeeLocations: List<PayeeLocation> = emptyList(),
    categoryGroups: List<CategoryGroup> = emptyList(),
    categories: List<Category> = emptyList(),
    months: List<Month> = emptyList(),
    transactions: List<Transaction> = emptyList(),
    subtransactions: List<Subtransaction> = emptyList(),
    scheduledTransactions: List<ScheduledTransaction> = emptyList(),
    scheduledSubtransactions: List<ScheduledSubtransaction> = emptyList()
): Budget.BudgetDetail {
    return Budget.BudgetDetail(
        accounts = accounts,
        payees = payees,
        payeeLocations = payeeLocations,
        categoryGroups = categoryGroups,
        categories = categories,
        months = months,
        transactions = transactions,
        subtransactions = subtransactions,
        scheduledTransactions = scheduledTransactions,
        scheduledSubtransactions = scheduledSubtransactions
    )
}
