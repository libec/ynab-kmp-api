package fixtures

import features.category.Category

fun Category.Companion.fixture(
    id: String = "healthcare-1zs",
    categoryGroupId: String = "cat-group-health",
    categoryGroupName: String = "Wellness",
    name: String = "Healthcare",
    hidden: Boolean = false,
    originalCategoryGroupId: String = "orig-cat1-id",
    note: String = "For keeping me alive for a bit",
    budgeted: Long = 20000,
    activity: Long = 15000,
    balance: Long = 5000,
    goalType: String = "monthly",
    goalDay: Int = 1,
    goalCadence: Int = 1,
    goalCadenceFrequency: Int = 1,
    goalCreationMonth: String = "2024-05-01",
    goalTarget: Long = 20000,
    goalTargetMonth: String = "2024-05-01",
    goalPercentageComplete: Int = 75,
    goalMonthsToBudget: Int = 1,
    goalUnderFunded: Long = 5000,
    goalOverallFunded: Long = 15000,
    goalOverallLeft: Long = 5000,
    deleted: Boolean = false
): Category {
    return Category(
        id = id,
        categoryGroupId = categoryGroupId,
        categoryGroupName = categoryGroupName,
        name = name,
        hidden = hidden,
        originalCategoryGroupId = originalCategoryGroupId,
        note = note,
        budgeted = budgeted,
        activity = activity,
        balance = balance,
        goalType = goalType,
        goalDay = goalDay,
        goalCadence = goalCadence,
        goalCadenceFrequency = goalCadenceFrequency,
        goalCreationMonth = goalCreationMonth,
        goalTarget = goalTarget,
        goalTargetMonth = goalTargetMonth,
        goalPercentageComplete = goalPercentageComplete,
        goalMonthsToBudget = goalMonthsToBudget,
        goalUnderFunded = goalUnderFunded,
        goalOverallFunded = goalOverallFunded,
        goalOverallLeft = goalOverallLeft,
        deleted = deleted
    )
}
