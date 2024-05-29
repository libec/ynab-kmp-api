package fixtures

import features.payee.Payee

fun Payee.Companion.fixture(
    id: String = "payee-id-139cvw",
    name: String = "Drug store",
    transferAccountId: String = "drug-tranfid1",
    deleted: Boolean = false
): Payee {
    return Payee(
        id = id,
        name = name,
        transferAccountId = transferAccountId,
        deleted = deleted
    )
}
