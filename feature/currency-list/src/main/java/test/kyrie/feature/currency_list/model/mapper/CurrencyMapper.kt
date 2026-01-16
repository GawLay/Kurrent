package test.kyrie.feature.currency_list.model.mapper

import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.feature.currency_list.model.Currency
import test.kyrie.feature.currency_list.model.SavedConversion


fun CurrencyDomain.toUi(): Currency {
    return Currency(
        currencyCode = code,
        currencyName = name,
        countryName = countryName,
        countryCode = countryCode,
        rateToUsd = rateToUsd,
        iconUrl = iconUrl,
        isAvailable = isAvailable
    )
}

fun List<CurrencyDomain>.toUi(): List<Currency> {
    return filter { it.isAvailable }
        .map { it.toUi() }
}

fun SaveConversionDomain?.toUi(): SavedConversion? {
    if (this == null) return null
    return SavedConversion(
        fromAmount = fromAmount,
        fromCurrency = fromCurrency,
        toAmount = toAmount,
        toCurrency = toCurrency,
        timestamp = timestamp
    )
}
