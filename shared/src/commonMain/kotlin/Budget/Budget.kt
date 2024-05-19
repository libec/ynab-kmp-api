package Budget

import kotlinx.serialization.Serializable

@Serializable
data class Budget(
    val id: String,
    val name: String
)