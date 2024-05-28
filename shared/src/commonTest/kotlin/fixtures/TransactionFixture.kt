package fixtures

import features.transaction.Transaction

fun Transaction.Companion.fixture(
    id: String = "transa1-13z_1d3",
    date: String = "2024-05-18",
    amount: Long = 150000,
    memo: String = "Car purchase",
    cleared: String = "cleared",
    approved: Boolean = true,
    flagColor: String = "black",
    flagName: String = "Black Car Priority",
    accountId: String = "acc-check-1",
    payeeId: String = "payee-14",
    categoryId: String = "cat0134",
    transferAccountId: String = "tracc4",
    transferTransactionId: String = "66afa6",
    matchedTransactionId: String = "b3fc",
    importId: String = "impoid12",
    importPayeeName: String = "Subaru dealership",
    importPayeeNameOriginal: String = "Subaru",
    debtTransactionType: String = "regular",
    deleted: Boolean = false
): Transaction {
    return Transaction(
        id = id,
        date = date,
        amount = amount,
        memo = memo,
        cleared = cleared,
        approved = approved,
        flagColor = flagColor,
        flagName = flagName,
        accountId = accountId,
        payeeId = payeeId,
        categoryId = categoryId,
        transferAccountId = transferAccountId,
        transferTransactionId = transferTransactionId,
        matchedTransactionId = matchedTransactionId,
        importId = importId,
        importPayeeName = importPayeeName,
        importPayeeNameOriginal = importPayeeNameOriginal,
        debtTransactionType = debtTransactionType,
        deleted = deleted
    )
}
