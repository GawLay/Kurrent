package test.kyrie.feature.currency_list.model


data class Currency(
    val code: String,
    val name: String,
    val flagEmoji: String,
    val exchangeRate: Double
)


/**
 *  saved currency conversion displayed in Quick Look
 */
data class SavedConversion(
    val fromAmount: String,
    val fromCurrency: String,
    val toAmount: String,
    val toCurrency: String,
    val timestamp: String = ""
)


data class AvailableCurrencies(
    // like key and value
    val currencies: Map<String, String>
)
