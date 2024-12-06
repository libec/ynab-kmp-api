package fixtures

import infrastructure.formats.DateFormat

fun DateFormat.Companion.fixture(
    format: String = "yyyy-MM-dd"
): DateFormat {
    return DateFormat(format = format)
}
