package infrastructure.formats

import kotlinx.serialization.Serializable

@Serializable
data class DateFormat(
    val format: String
)