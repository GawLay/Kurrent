package test.kyrie.feature.calculator.model

data class Currency(
    val currencyCode: String,
    val currencyName: String,
    val countryName: String,
    val countryCode: String,
    val rateToUsd: String,
    val iconUrl: String,
    val isAvailable: Boolean
)
