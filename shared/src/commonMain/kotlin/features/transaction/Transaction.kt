package features.transaction

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: String,
    val date: String,
    val amount: Long,
    val memo: String?,
    val cleared: String,
    val approved: Boolean,
    val flagColor: String?,
    val flagName: String?,
    val accountId: String,
    val payeeId: String?,
    val categoryId: String?,
    val transferAccountId: String?,
    val transferTransactionId: String?,
    val matchedTransactionId: String?,
    val importId: String?,
    val importPayeeName: String?,
    val importPayeeNameOriginal: String?,
    val debtTransactionType: String?,
    val deleted: Boolean
)
