package features.category

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface CategoriesRepository {
    val categories: StateFlow<List<CategoryGroup>>

    suspend fun fetchCategories(budgetId: String)
}

internal class CategoriesRepositoryImpl(
    private val resource: CategoriesResource
) : CategoriesRepository {

    private val mutableCategories = MutableStateFlow(emptyList<CategoryGroup>())

    override val categories: StateFlow<List<CategoryGroup>>
        get() = mutableCategories.asStateFlow()

    override suspend fun fetchCategories(budgetId: String) {
        resource.getCategories(budgetId).let { categories ->
            mutableCategories.value = categories
        }
    }
}