import features.account.Account
import features.budget.Budget
import infrastructure.formats.CurrencyFormat
import infrastructure.formats.DateFormat
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class BudgetTests {
    @Test
    fun `it downloads all budgets with injected access token`() = runBlocking {
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            mockedResponseFileName = "BudgetsResponse.json",
            endpoint = "budgets"
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
        val budgetRepository = ynabSession.getBudgetsRepository()

        budgetRepository.fetchAllBudgets()

        val expectedBudgets = listOf(
            Budget(
                id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                name = "Personal Budget",
                lastModifiedOn = "2024-05-18T09:54:28.054Z",
                firstMonth = "2024-05-01",
                lastMonth = "2024-05-31",
                dateFormat = DateFormat(format = "yyyy-MM-dd"),
                currencyFormat = CurrencyFormat(
                    isoCode = "USD",
                    exampleFormat = "$1,234.56",
                    decimalDigits = 2,
                    decimalSeparator = ".",
                    symbolFirst = true,
                    groupSeparator = ",",
                    currencySymbol = "$",
                    displaySymbol = true
                ),
                accounts = listOf(
                    Account(
                        id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        name = "Checking Account",
                        type = "checking",
                        onBudget = true,
                        closed = false,
                        note = "Main checking account",
                        balance = -6942931520,
                        clearedBalance = 0,
                        unclearedBalance = 0,
                        transferPayeeId = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        directImportLinked = true,
                        directImportInError = false,
                        lastReconciledAt = "2024-05-18T09:54:28.054Z",
                        debtOriginalBalance = null,
                        debtInterestRates = mapOf(
                            "additionalProp1" to 0,
                            "additionalProp2" to 0,
                            "additionalProp3" to 0
                        ),
                        debtMinimumPayments = mapOf(
                            "additionalProp1" to 0,
                            "additionalProp2" to 0,
                            "additionalProp3" to 0
                        ),
                        debtEscrowAmounts = mapOf(
                            "additionalProp1" to 0,
                            "additionalProp2" to 0,
                            "additionalProp3" to 0
                        ),
                        deleted = false
                    )
                )
            ),
            Budget(
                id = "3fa85f64-5717-4562-b3fc-2c963f66afa7",
                name = "Business Budget",
                lastModifiedOn = "2024-05-18T09:54:28.054Z",
                firstMonth = "2024-05-01",
                lastMonth = "2024-05-31",
                dateFormat = DateFormat(format = "yyyy-MM-dd"),
                currencyFormat = CurrencyFormat(
                    isoCode = "USD",
                    exampleFormat = "$1,234.56",
                    decimalDigits = 2,
                    decimalSeparator = ".",
                    symbolFirst = true,
                    groupSeparator = ",",
                    currencySymbol = "$",
                    displaySymbol = true
                ),
                accounts = listOf(
                    Account(
                        id = "3fa85f64-5717-4562-b3fc-2c963f66afa7",
                        name = "Business Account",
                        type = "checking",
                        onBudget = true,
                        closed = false,
                        note = "Main business account",
                        balance = 1000000,
                        clearedBalance = 1000000,
                        unclearedBalance = 0,
                        transferPayeeId = "3fa85f64-5717-4562-b3fc-2c963f66afa7",
                        directImportLinked = true,
                        directImportInError = false,
                        lastReconciledAt = "2024-05-18T09:54:28.054Z",
                        debtOriginalBalance = null,
                        debtInterestRates = mapOf(),
                        debtMinimumPayments = mapOf(),
                        debtEscrowAmounts = mapOf(),
                        deleted = false
                    )
                )
            )
        )

        val expectedDefaultBudget = Budget(
            id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            name = "Default Budget",
            lastModifiedOn = "2024-05-18T09:54:28.054Z",
            firstMonth = "2024-05-01",
            lastMonth = "2024-05-31",
            dateFormat = DateFormat(format = "yyyy-MM-dd"),
            currencyFormat = CurrencyFormat(
                isoCode = "USD",
                exampleFormat = "$1,234.56",
                decimalDigits = 2,
                decimalSeparator = ".",
                symbolFirst = true,
                groupSeparator = ",",
                currencySymbol = "$",
                displaySymbol = true
            ),
            accounts = listOf(
                Account(
                    id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    name = "Savings Account",
                    type = "savings",
                    onBudget = true,
                    closed = false,
                    note = "Emergency savings",
                    balance = 500000,
                    clearedBalance = 500000,
                    unclearedBalance = 0,
                    transferPayeeId = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    directImportLinked = true,
                    directImportInError = false,
                    lastReconciledAt = "2024-05-18T09:54:28.054Z",
                    debtOriginalBalance = null,
                    debtInterestRates = mapOf(
                        "additionalProp1" to 0,
                        "additionalProp2" to 0,
                        "additionalProp3" to 0
                    ),
                    debtMinimumPayments = mapOf(
                        "additionalProp1" to 0,
                        "additionalProp2" to 0,
                        "additionalProp3" to 0
                    ),
                    debtEscrowAmounts = mapOf(
                        "additionalProp1" to 0,
                        "additionalProp2" to 0,
                        "additionalProp3" to 0
                    ),
                    deleted = false
                )
            )
        )

        assertEquals(expectedBudgets, budgetRepository.budgets.value)
        assertEquals(expectedDefaultBudget, budgetRepository.selectedBudget.value)
    }
}
