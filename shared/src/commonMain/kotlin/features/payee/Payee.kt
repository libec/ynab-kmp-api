package features.payee

import kotlinx.serialization.Serializable

@Serializable
data class Payee(
    val id: String,
    val name: String,
    val transferAccountId: String?,
    val deleted: Boolean
)