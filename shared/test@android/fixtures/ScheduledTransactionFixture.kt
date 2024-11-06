package fixtures

import features.transaction.ScheduledTransaction

fun ScheduledTransaction.Companion.fixture(
    id: String = "schedtransid-31431",
    dateFirst: String = "2024-05-01",
    dateNext: String = "2024-06-01",
    frequency: String = "monthly",
    amount: Long = 1000,
    memo: String = "Rent payment",
    flagColor: String = "green",
    flagName: String = "Rent",
    accountId: String = "acc-0",
    payeeId: String = "poayee-13",
    categoryId: String = "catm_c1",
    transferAccountId: String = "trnzc-11z02",
    deleted: Boolean = false
): ScheduledTransaction {
    return ScheduledTransaction(
        id = id,
        dateFirst = dateFirst,
        dateNext = dateNext,
        frequency = frequency,
        amount = amount,
        memo = memo,
        flagColor = flagColor,
        flagName = flagName,
        accountId = accountId,
        payeeId = payeeId,
        categoryId = categoryId,
        transferAccountId = transferAccountId,
        deleted = deleted
    )
}
