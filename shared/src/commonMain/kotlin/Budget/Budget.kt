package Budget

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

@Serializable
data class DateFormat(
    val format: String
)

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

@Serializable
data class Account(
    val id: String,
    val name: String,
    val type: String,
    @SerialName("on_budget")
    val onBudget: Boolean,
    val closed: Boolean,
    val note: String?,
    val balance: Long,
    @SerialName("cleared_balance")
    val clearedBalance: Long,
    @SerialName("uncleared_balance")
    val unclearedBalance: Long,
    @SerialName("transfer_payee_id")
    val transferPayeeId: String,
    @SerialName("direct_import_linked")
    val directImportLinked: Boolean,
    @SerialName("direct_import_in_error")
    val directImportInError: Boolean,
    @SerialName("last_reconciled_at")
    val lastReconciledAt: String?,
    @SerialName("debt_original_balance")
    val debtOriginalBalance: Long?,
    @SerialName("debt_interest_rates")
    val debtInterestRates: Map<String, Int>,
    @SerialName("debt_minimum_payments")
    val debtMinimumPayments: Map<String, Int>,
    @SerialName("debt_escrow_amounts")
    val debtEscrowAmounts: Map<String, Int>,
    val deleted: Boolean
)
