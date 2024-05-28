package fixtures

import features.transaction.Subtransaction

fun Subtransaction.Companion.fixture(
    id: String = "subtrans-1391223",
    transactionId: String = "trans-zzzcd",
    amount: Long = 5000,
    memo: String = "Half of the rent",
    payeeId: String = "roomie-4",
    payeeName: String = "Roommate",
    categoryId: String = "cat-rent3",
    categoryName: String = "Rent",
    transferAccountId: String = "rac-tran3",
    transferTransactionId: String = "10awd",
    deleted: Boolean = false
): Subtransaction {
    return Subtransaction(
        id = id,
        transactionId = transactionId,
        amount = amount,
        memo = memo,
        payeeId = payeeId,
        payeeName = payeeName,
        categoryId = categoryId,
        categoryName = categoryName,
        transferAccountId = transferAccountId,
        transferTransactionId = transferTransactionId,
        deleted = deleted
    )
}
