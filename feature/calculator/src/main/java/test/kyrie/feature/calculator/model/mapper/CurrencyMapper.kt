package test.kyrie.feature.calculator.model.mapper

import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.feature.calculator.model.Currency

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

fun Currency.toDomain(): CurrencyDomain {
    return CurrencyDomain(
        code = currencyCode,
        name = currencyName,
        countryName = countryName,
        countryCode = countryCode,
        rateToUsd = rateToUsd,
        iconUrl = iconUrl,
        isAvailable = isAvailable
    )
}
