package fixtures

import features.category.Category
import features.category.CategoryGroup

fun CategoryGroup.Companion.fixture(
    id: String = "cat-group-health",
    name: String = "Wellness",
    hidden: Boolean = false,
    deleted: Boolean = false,
    categories: List<Category> = emptyList(),
): CategoryGroup {
    return CategoryGroup(
        id = id,
        name = name,
        hidden = hidden,
        deleted = deleted,
        categories = categories
    )
}