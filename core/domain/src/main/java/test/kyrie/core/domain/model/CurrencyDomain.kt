package test.kyrie.core.domain.model

data class CurrencyDomain(
    val code: String,
    val name: String,
    val countryName: String,
    val countryCode: String,
    val rateToUsd: String,
    val iconUrl: String,
    val isAvailable: Boolean,
)
