package fixtures

import features.account.Account
import features.budget.Budget
import infrastructure.formats.CurrencyFormat
import infrastructure.formats.DateFormat

fun Budget.Companion.fixture(
    id: String = "aewf3123",
    name: String = "Personal budget",
    lastModifiedOn: String = "2024-05-18T09:54:28.054Z",
    firstMonth: String = "2024-05-01",
    lastMonth: String = "2024-05-31",
    dateFormat: DateFormat = DateFormat.fixture(),
    currencyFormat: CurrencyFormat = CurrencyFormat.fixture(),
    accounts: List<Account> = listOf(Account.fixture())
): Budget {
    return Budget(
        id = id,
        name = name,
        lastModifiedOn = lastModifiedOn,
        firstMonth = firstMonth,
        lastMonth = lastMonth,
        dateFormat = dateFormat,
        currencyFormat = currencyFormat,
        accounts = accounts
    )
}
