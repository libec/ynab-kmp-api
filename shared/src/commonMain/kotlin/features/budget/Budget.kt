package features.budget

import features.account.Account
import infrastructure.formats.CurrencyFormat
import infrastructure.formats.DateFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Budget(
    val id: String,
    val name: String,
    @SerialName("last_modified_on")
    val lastModifiedOn: String,
    @SerialName("first_month")
    val firstMonth: String,
    @SerialName("last_month")
    val lastMonth: String,
    @SerialName("date_format")
    val dateFormat: DateFormat,
    @SerialName("currency_format")
    val currencyFormat: CurrencyFormat,
    val accounts: List<Account>
)
