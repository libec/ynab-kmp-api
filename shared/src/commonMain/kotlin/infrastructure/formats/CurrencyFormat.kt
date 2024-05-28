package infrastructure.formats

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyFormat(
    val isoCode: String,
    val exampleFormat: String,
    val decimalDigits: Int,
    val decimalSeparator: String,
    val symbolFirst: Boolean,
    val groupSeparator: String,
    val currencySymbol: String,
    val displaySymbol: Boolean
)