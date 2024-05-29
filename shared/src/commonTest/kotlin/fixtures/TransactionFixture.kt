package fixtures

import features.transaction.Subtransaction
import features.transaction.Transaction

fun Transaction.Companion.summaryFixture(): Transaction {
    return Transaction.fixture(
        accountName = null,
        payeeName = null,
        categoryName = null,
        subtransactions = emptyList()
    )
}

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
    accountName: String? = "Checking Account",
    payeeName: String? = "Grocery Store",
    categoryName: String? = "Health",
    transferAccountId: String = "tracc4",
    transferTransactionId: String = "66afa6",
    matchedTransactionId: String = "b3fc",
    importId: String = "impoid12",
    importPayeeName: String = "Subaru dealership",
    importPayeeNameOriginal: String = "Subaru",
    debtTransactionType: String = "regular",
    deleted: Boolean = false,
    subtransactions: List<Subtransaction> = listOf(Subtransaction.fixture())
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
        accountName = accountName,
        payeeName = payeeName,
        categoryName = categoryName,
        transferAccountId = transferAccountId,
        transferTransactionId = transferTransactionId,
        matchedTransactionId = matchedTransactionId,
        importId = importId,
        importPayeeName = importPayeeName,
        importPayeeNameOriginal = importPayeeNameOriginal,
        debtTransactionType = debtTransactionType,
        deleted = deleted,
        subtransactions = subtransactions
    )
}
