[YNAB](https://www.ynab.com/) (You Need A Budget) is a great piece of budgeting software with a [public API](https://api.ynab.com/). This is a multiplatform Kotlin library for that API that compiles for JVM for Android and LLVM for iOS.

The feature set is not 1:1 to the public API, but it's a good starting point built on solid foundations.

## Motivation
I'm an iOS developer interested in sharing code across platforms without compromising the native experience. I knew zero Kotlin before this, but learning by doing is my jam, and doing something useful like sharing data access code is a great starting point that is just a step away from sharing business logic to ensure consistency across the whole product.

## Usage

Check `EndToEndTests` in Kotlin and in Swift for a executable example of how to use the library.

Replace null with your token (also, don't commit your access token to git):

```kotlin
val accessToken: String? = null
```

Inject your access token to start a session:
```kotlin
val ynabSession = YnabSession(UserAuthentication(accessToken))
```

Session scoped repositories hold your data for the lifetime of the session:
```kotlin
val budgetRepository = ynabSession.getBudgetsRepository()
val accountsRepository = ynabSession.getAccountsRepository()
val payeesRepository = ynabSession.getPayeesRepository()
val categoriesRepository = ynabSession.getCategoriesRepository()
val transactionsRepository = ynabSession.getTransactionsRepository()
```

Fetch your data:
```kotlin
budgetRepository.fetchAllBudgets()
accountsRepository.fetchAccounts(budgetId = budget.id)
payeesRepository.fetchPayees(budgetId = budget.id)
categoriesRepository.fetchCategories(budgetId = budget.id)
```

Subscribe to updates:
```kotlin
budgetRepository.budgets.map { budgets ->
    // Do your domain or presentation magic here
}

categoriesRepository.categories.map { categories ->
    // Do your domain or presentation magic here
}
```

## Why bother with Repositories and not just expose Resources
To [quote myself](https://mobileit.cz/Blog/Pages/swift-ui-and-architecture-state.aspx):

> A repository is a design pattern that abstracts data access logic from business access logic by exposing the collection-like interface to access business entities. The actual implementation of the interface might communicate with the database,  REST API, and what your project requires...

A repository is a great place for possible implementation of caching and compilation of delta requests as described in the [public API](https://api.ynab.com/).
