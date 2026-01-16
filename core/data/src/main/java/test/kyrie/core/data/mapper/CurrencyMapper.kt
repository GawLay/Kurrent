package test.kyrie.core.data.mapper

import test.kyrie.core.data.local.entity.CurrencyEntity
import test.kyrie.core.data.local.entity.SaveConversionEntity
import test.kyrie.core.data.model.CurrencyDto
import test.kyrie.core.data.util.DataDefaultCurrency
import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.model.SaveConversionDomain

fun CurrencyDto.toEntity(rateToUsd: String): CurrencyEntity {
    return CurrencyEntity(
        code = currencyCode ?: DataDefaultCurrency.DEFAULT_CURRENCY_CODE.defaultValue,
        name = currencyName ?: DataDefaultCurrency.DEFAULT_CURRENCY_NAME.defaultValue,
        countryName = countryName ?: DataDefaultCurrency.DEFAULT_COUNTRY_NAME.defaultValue,
        countryCode = countryCode ?: DataDefaultCurrency.DEFAULT_COUNTRY_CODE.defaultValue,
        rateToUsd = rateToUsd,
        iconUrl = icon ?: DataDefaultCurrency.DEFAULT_ICON_URL.defaultValue,
        isAvailable = status.equals("available", ignoreCase = true)
    )
}

fun CurrencyEntity.toDomain(): CurrencyDomain {
    return CurrencyDomain(
        code = code,
        name = name,
        countryName = countryName,
        countryCode = countryCode,
        rateToUsd = rateToUsd,
        iconUrl = iconUrl,
        isAvailable = isAvailable
    )
}

fun List<CurrencyEntity>.toDomain(): List<CurrencyDomain> {
    return map { it.toDomain() }
}

fun SaveConversionDomain.toEntity(): SaveConversionEntity {
    return SaveConversionEntity(
        fromAmount = fromAmount,
        fromCurrency = fromCurrency,
        toAmount = toAmount,
        toCurrency = toCurrency,
        timestamp = timestamp
    )
}

fun SaveConversionEntity?.toDomain(): SaveConversionDomain? {
    if (this == null) return null
    return SaveConversionDomain(
        fromAmount = fromAmount,
        fromCurrency = fromCurrency,
        toAmount = toAmount,
        toCurrency = toCurrency,
        timestamp = timestamp
    )
}

