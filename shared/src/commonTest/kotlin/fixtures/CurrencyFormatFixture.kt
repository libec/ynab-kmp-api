package fixtures

import infrastructure.formats.CurrencyFormat

fun CurrencyFormat.Companion.fixture(
    isoCode: String = "USD",
    exampleFormat: String = "$1,234.56",
    decimalDigits: Int = 2,
    decimalSeparator: String = ".",
    symbolFirst: Boolean = true,
    groupSeparator: String = ",",
    currencySymbol: String = "$",
    displaySymbol: Boolean = true
): CurrencyFormat {
    return CurrencyFormat(
        isoCode = isoCode,
        exampleFormat = exampleFormat,
        decimalDigits = decimalDigits,
        decimalSeparator = decimalSeparator,
        symbolFirst = symbolFirst,
        groupSeparator = groupSeparator,
        currencySymbol = currencySymbol,
        displaySymbol = displaySymbol
    )
}