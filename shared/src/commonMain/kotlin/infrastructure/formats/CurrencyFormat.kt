package infrastructure.formats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyFormat(
    @SerialName("iso_code")
    val isoCode: String,
    @SerialName("example_format")
    val exampleFormat: String,
    @SerialName("decimal_digits")
    val decimalDigits: Int,
    @SerialName("decimal_separator")
    val decimalSeparator: String,
    @SerialName("symbol_first")
    val symbolFirst: Boolean,
    @SerialName("group_separator")
    val groupSeparator: String,
    @SerialName("currency_symbol")
    val currencySymbol: String,
    @SerialName("display_symbol")
    val displaySymbol: Boolean
)