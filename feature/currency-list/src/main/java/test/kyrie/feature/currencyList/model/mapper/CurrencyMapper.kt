package test.kyrie.feature.currencyList.model.mapper

import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.feature.currencyList.model.Currency
import test.kyrie.feature.currencyList.model.SavedConversion

fun CurrencyDomain.toUi(): Currency =
    Currency(
        currencyCode = code,
        currencyName = name,
        countryName = countryName,
        countryCode = countryCode,
        rateToUsd = rateToUsd,
        iconUrl = iconUrl,
        isAvailable = isAvailable,
    )

fun List<CurrencyDomain>.toUi(): List<Currency> =
    filter { it.isAvailable }
        .map { it.toUi() }

fun SaveConversionDomain?.toUi(): SavedConversion? {
    if (this == null) return null
    return SavedConversion(
        fromAmount = fromAmount,
        fromCurrency = fromCurrency,
        toAmount = toAmount,
        toCurrency = toCurrency,
        timestamp = timestamp,
    )
}
