package test.kyrie.feature.currency_list.model

/**
 * Represents a currency with its details
 */
data class Currency(
    val code: String,
    val name: String,
    val flagEmoji: String,
    val exchangeRate: Double
)

/**
 * Represents the saved currency conversion displayed in Quick Look
 */
data class SavedConversion(
    val fromAmount: String,
    val fromCurrency: String,
    val toAmount: String,
    val toCurrency: String
)
