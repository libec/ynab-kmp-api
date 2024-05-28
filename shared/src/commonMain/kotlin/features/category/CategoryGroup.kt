package features.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryGroup(
    val id: String,
    val name: String,
    val hidden: Boolean,
    val deleted: Boolean
)