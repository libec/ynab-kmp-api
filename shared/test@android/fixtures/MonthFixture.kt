package fixtures

import features.category.Category
import features.month.Month

fun Month.Companion.fixture(
    month: String = "2024-05-01",
    note: String = "May budget",
    income: Long = 300000,
    budgeted: Long = 250000,
    activity: Long = 200000,
    toBeBudgeted: Long = 50000,
    ageOfMoney: Int = 30,
    deleted: Boolean = false,
    categories: List<Category> = listOf(Category.fixture())
): Month {
    return Month(
        month = month,
        note = note,
        income = income,
        budgeted = budgeted,
        activity = activity,
        toBeBudgeted = toBeBudgeted,
        ageOfMoney = ageOfMoney,
        deleted = deleted,
        categories = categories
    )
}
