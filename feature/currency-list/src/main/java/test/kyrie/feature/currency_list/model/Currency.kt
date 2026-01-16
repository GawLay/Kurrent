package test.kyrie.feature.currency_list.model


data class Currency(
    val currencyCode: String,
    val currencyName: String,
    val countryName: String,
    val countryCode: String,
    val rateToUsd: String,
    val iconUrl: String,
    val isAvailable: Boolean
)

/**
 *  saved currency conversion displayed in Quick Look
 */
data class SavedConversion(
    val fromAmount: String,
    val fromCurrency: String,
    val toAmount: String,
    val toCurrency: String,
    val timestamp: Long = 0
)
