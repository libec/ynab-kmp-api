package fixtures

import features.category.CategoryGroup

fun CategoryGroup.Companion.fixture(
    id: String = "cat-group-health",
    name: String = "Wellness",
    hidden: Boolean = false,
    deleted: Boolean = false
): CategoryGroup {
    return CategoryGroup(
        id = id,
        name = name,
        hidden = hidden,
        deleted = deleted
    )
}