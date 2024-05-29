import features.budget.Budget
import features.category.Category
import features.category.CategoryGroup
import fixtures.fixture
import kotlinx.coroutines.runBlocking
import mocks.NetworkClientMockFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoriesTests {

    @Test
    fun `it downloads and stores categories`() = runBlocking {
        val budgetId = Budget.fixture().id
        val mockedNetworkClient = NetworkClientMockFactory().makeMockedNetworkClient(
            listOf(Pair("budgets/$budgetId/categories", "categories.json"))
        )
        val ynabSession = YnabSession(userAuthentication = UserAuthentication("ae2e03aaew3"))
        ynabSession.loginScope.declare(mockedNetworkClient)
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