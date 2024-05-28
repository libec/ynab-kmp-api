package features.transaction

import kotlinx.serialization.Serializable

@Serializable
data class ScheduledSubtransaction(
    val id: String,
    val scheduledTransactionId: String,
    val amount: Long,
    val memo: String?,
    val payeeId: String?,
    val categoryId: String?,
    val transferAccountId: String?,
    val deleted: Boolean
)