package test.kyrie.core.domain.model

import java.math.BigDecimal

data class Currency(
    val code: String,
    val name: String,
    val countryName: String,
    val countryCode: String,
    val rateToUsd: BigDecimal,
    val iconUrl: String?,
    val isAvailable: Boolean
)