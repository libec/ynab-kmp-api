package features.transaction

import kotlinx.serialization.Serializable

@Serializable
data class ScheduledTransaction(
    val id: String,
    val dateFirst: String,
    val dateNext: String,
    val frequency: String,
    val amount: Long,
    val memo: String?,
    val flagColor: String?,
    val flagName: String?,
    val accountId: String,
    val payeeId: String?,
    val categoryId: String?,
    val transferAccountId: String?,
    val deleted: Boolean
)
