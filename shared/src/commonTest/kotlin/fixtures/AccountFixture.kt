package fixtures

import features.account.Account

fun Account.Companion.fixture(
    id: String = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    name: String = "Savings Account",
    type: String = "savings",
    onBudget: Boolean = true,
    closed: Boolean = false,
    note: String? = "Emergency savings",
    balance: Long = 500000,
    clearedBalance: Long = 500000,
    unclearedBalance: Long = 0,
    transferPayeeId: String = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    directImportLinked: Boolean = true,
    directImportInError: Boolean = false,
    lastReconciledAt: String? = "2024-05-18T09:54:28.054Z",
    debtOriginalBalance: Long? = null,
    debtInterestRates: Map<String, Int> = mapOf(
        "additionalProp1" to 0,
        "additionalProp2" to 0,
        "additionalProp3" to 0
    ),
    debtMinimumPayments: Map<String, Int> = mapOf(
        "additionalProp1" to 0,
        "additionalProp2" to 0,
        "additionalProp3" to 0
    ),
    debtEscrowAmounts: Map<String, Int> = mapOf(
        "additionalProp1" to 0,
        "additionalProp2" to 0,
        "additionalProp3" to 0
    ),
    deleted: Boolean = false
): Account {
    return Account(
        id = id,
        name = name,
        type = type,
        onBudget = onBudget,
        closed = closed,
        note = note,
        balance = balance,
        clearedBalance = clearedBalance,
        unclearedBalance = unclearedBalance,
        transferPayeeId = transferPayeeId,
        directImportLinked = directImportLinked,
        directImportInError = directImportInError,
        lastReconciledAt = lastReconciledAt,
        debtOriginalBalance = debtOriginalBalance,
        debtInterestRates = debtInterestRates,
        debtMinimumPayments = debtMinimumPayments,
        debtEscrowAmounts = debtEscrowAmounts,
        deleted = deleted
    )
}