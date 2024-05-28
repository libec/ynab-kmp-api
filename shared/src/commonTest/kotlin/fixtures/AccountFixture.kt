package fixtures

import features.account.Account

fun Account.Companion.fixture(
    id: String = "acc-id-14llcd",
    name: String = "Checking Account",
    type: String = "checking-bank",
    onBudget: Boolean = true,
    closed: Boolean = false,
    note: String? = "Main checking account in the bank",
    balance: Long = 312000,
    clearedBalance: Long = 315000,
    unclearedBalance: Long = 3000,
    transferPayeeId: String = "payee-1432",
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