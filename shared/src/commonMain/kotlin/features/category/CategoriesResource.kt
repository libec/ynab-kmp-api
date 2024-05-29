package features.category

import infrastructure.networking.NetworkClient
import kotlinx.serialization.Serializable

internal interface CategoriesResource {
    suspend fun getCategories(budgetId: String): List<CategoryGroup>
}

internal class CategoriesRestResource(
    private val networkClient: NetworkClient
) : CategoriesResource {

    override suspend fun getCategories(budgetId: String): List<CategoryGroup> {
        val response: ApiResponse.CategoriesResponse =
            networkClient.get("/budgets/$budgetId/categories")
        return response.data.categoryGroups
    }
}

private object ApiResponse {
    @Serializable
    data class CategoriesResponse(val data: Categories)

    @Serializable
    data class Categories(
        val categoryGroups: List<CategoryGroup>,
    )
}