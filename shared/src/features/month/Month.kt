package features.month

import features.category.Category
import kotlinx.serialization.Serializable

@Serializable
data class Month(
    val month: String,
    val note: String?,
    val income: Long,
    val budgeted: Long,
    val activity: Long,
    val toBeBudgeted: Long,
    val ageOfMoney: Int?,
    val deleted: Boolean,
    val categories: List<Category>
)
