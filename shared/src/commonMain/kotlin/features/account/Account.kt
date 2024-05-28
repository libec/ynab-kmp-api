package features.account

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: String,
    val name: String,
    val type: String,
    val onBudget: Boolean,
    val closed: Boolean,
    val note: String?,
    val balance: Long,
    val clearedBalance: Long,
    val unclearedBalance: Long,
    val transferPayeeId: String,
    val directImportLinked: Boolean,
    val directImportInError: Boolean,
    val lastReconciledAt: String?,
    val debtOriginalBalance: Long?,
    val debtInterestRates: Map<String, Int>,
    val debtMinimumPayments: Map<String, Int>,
    val debtEscrowAmounts: Map<String, Int>,
    val deleted: Boolean
)