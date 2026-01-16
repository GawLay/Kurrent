package test.kyrie.core.data.mapper

import test.kyrie.core.data.local.entity.CurrencyEntity
import test.kyrie.core.data.model.CurrencyDto
import test.kyrie.core.domain.model.Currency
import java.math.BigDecimal

fun CurrencyDto.toEntity(rateToUsd: String): CurrencyEntity {
    return CurrencyEntity(
        code = currencyCode,
        name = currencyName,
        countryName = countryName,
        countryCode = countryCode,
        rateToUsd = rateToUsd,
        iconUrl = icon,
        isAvailable = status.equals("available", ignoreCase = true)
    )
}

fun CurrencyEntity.toDomain(): Currency {
    return Currency(
        code = code,
        name = name,
        countryName = countryName,
        countryCode = countryCode,
        rateToUsd = BigDecimal(rateToUsd),
        iconUrl = iconUrl,
        isAvailable = isAvailable
    )
}

fun List<CurrencyEntity>.toDomain(): List<Currency> {
    return map { it.toDomain() }
}

