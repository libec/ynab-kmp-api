package features.transaction

import kotlinx.serialization.Serializable

@Serializable
data class Subtransaction(
    val id: String,
    val transactionId: String,
    val amount: Long,
    val memo: String?,
    val payeeId: String?,
    val payeeName: String?,
    val categoryId: String?,
    val categoryName: String?,
    val transferAccountId: String?,
    val transferTransactionId: String?,
    val deleted: Boolean
)
