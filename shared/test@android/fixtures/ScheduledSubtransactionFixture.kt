package fixtures

import features.transaction.ScheduledSubtransaction

fun ScheduledSubtransaction.Companion.fixture(
    id: String = "subtransid-104dasd3",
    scheduledTransactionId: String = "schedtransid-31431",
    amount: Long = 1000,
    memo: String = "Groceries",
    payeeId: String = "paz_12",
    categoryId: String = "caze01_1",
    transferAccountId: String = "caz_W2112",
    deleted: Boolean = true
): ScheduledSubtransaction {
    return ScheduledSubtransaction(
        id = id,
        scheduledTransactionId = scheduledTransactionId,
        amount = amount,
        memo = memo,
        payeeId = payeeId,
        categoryId = categoryId,
        transferAccountId = transferAccountId,
        deleted = deleted
    )
}