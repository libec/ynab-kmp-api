package features.category

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val categoryGroupId: String,
    val categoryGroupName: String?,
    val name: String,
    val hidden: Boolean,
    val originalCategoryGroupId: String?,
    val note: String?,
    val budgeted: Long,
    val activity: Long,
    val balance: Long,
    val goalType: String?,
    val goalDay: Int?,
    val goalCadence: Int?,
    val goalCadenceFrequency: Int?,
    val goalCreationMonth: String?,
    val goalTarget: Long?,
    val goalTargetMonth: String?,
    val goalPercentageComplete: Int?,
    val goalMonthsToBudget: Int?,
    val goalUnderFunded: Long?,
    val goalOverallFunded: Long?,
    val goalOverallLeft: Long?,
    val deleted: Boolean
)
