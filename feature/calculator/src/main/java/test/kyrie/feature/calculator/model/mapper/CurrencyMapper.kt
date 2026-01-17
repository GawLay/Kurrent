package test.kyrie.feature.calculator.model.mapper

import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.feature.calculator.model.Currency

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

fun Currency.toDomain(): CurrencyDomain =
    CurrencyDomain(
        code = currencyCode,
        name = currencyName,
        countryName = countryName,
        countryCode = countryCode,
        rateToUsd = rateToUsd,
        iconUrl = iconUrl,
        isAvailable = isAvailable,
    )
