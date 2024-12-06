package fixtures

import features.payee.PayeeLocation

fun PayeeLocation.Companion.fixture(
    id: String = "payee-loc-1231",
    payeeId: String = "payee-id-139cvw",
    latitude: String = "40.7128",
    longitude: String = "74.0060",
    deleted: Boolean = false
): PayeeLocation {
    return PayeeLocation(
        id = id,
        payeeId = payeeId,
        latitude = latitude,
        longitude = longitude,
        deleted = deleted
    )
}