package features.payee

import kotlinx.serialization.Serializable

@Serializable
data class PayeeLocation(
    val id: String,
    val payeeId: String,
    val latitude: String,
    val longitude: String,
    val deleted: Boolean
)
