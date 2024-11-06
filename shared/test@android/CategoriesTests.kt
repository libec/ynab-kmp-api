import features.category.Category
import features.category.CategoryGroup
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoriesTests: MockedResponseTests() {

    @Test
    fun `it downloads and stores categories`() = runBlocking {
        val mockedResponses = listOf(Pair("budgets/$budgetId/categories", "categories.json"))
        val ynabSession = makeYnabSession(mockedResponses)
        val categoriesRepository = ynabSession.getCategoriesRepository()

        categoriesRepository.fetchCategories(budgetId)

        val expectedCategories = listOf(
            CategoryGroup.fixture(
                categories = listOf(
                    Category.fixture(),
                    Category.fixture(id = "sports-1", name = "Sports"),
                    Category.fixture(id = "eating", name = "Healthy Eating"),
                )
            ),
            CategoryGroup.fixture(
                id = "fun-group",
                name = "Hobbies",
                categories = listOf(
                    Category.fixture(id = "bike-activies", name = "MTBiking"),
                    Category.fixture(id = "hiking-1a", name = "Hiking"),
                )
            )
        )

        assertEquals(expectedCategories, categoriesRepository.categories.value)
    }
}