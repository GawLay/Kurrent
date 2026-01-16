package test.kyrie.feature.currency_list.ui


import test.kyrie.feature.currency_list.model.Currency
import test.kyrie.feature.currency_list.model.SavedConversion


data class CurrencyListUiState(
    val isLoading: Boolean = false,
    val savedConversion: SavedConversion? = null,
    val currencies: List<Currency> = emptyList(),
    val selectedCurrency: String? = null,
    val baseCurrency: String = "USD",
    val availableCurrencies: Map<String, String> = emptyMap(),
    val error: String? = null
) {
    companion object {
        /**
         *  mock data for preview and testing
         */
        fun mockData(): CurrencyListUiState {
            return CurrencyListUiState(
                isLoading = false,
                savedConversion = SavedConversion(
                    fromAmount = "100",
                    fromCurrency = "JPY",
                    toAmount = "0.67",
                    toCurrency = "USD"
                ),
                baseCurrency = "USD",
                availableCurrencies = mapOf(
                    "USD" to "United States Dollar",
                    "EUR" to "Euro",
                    "GBP" to "British Pound",
                    "JPY" to "Japanese Yen",
                    "CAD" to "Canadian Dollar",
                    "AUD" to "Australian Dollar",
                    "CNY" to "Chinese Yuan",
                    "CHF" to "Swiss Franc",
                    "INR" to "Indian Rupee",
                    "KRW" to "South Korean Won",
                    "SGD" to "Singapore Dollar",
                    "HKD" to "Hong Kong Dollar"
                ),
                currencies = listOf(
                    Currency(
                        code = "JPY",
                        name = "Japanese Yen",
                        flagEmoji = "🇯🇵",
                        exchangeRate = 148.50
                    ),
                    Currency(
                        code = "USD",
                        name = "United States Dollar",
                        flagEmoji = "🇺🇸",
                        exchangeRate = 148.50
                    ),
                    Currency(
                        code = "GBP",
                        name = "British Pound",
                        flagEmoji = "🇬🇧",
                        exchangeRate = 148.00
                    ),
                    Currency(
                        code = "CAD",
                        name = "Canadian Dollar",
                        flagEmoji = "🇨🇦",
                        exchangeRate = 140.00
                    ),
                    Currency(
                        code = "AUD",
                        name = "Australian Dollar",
                        flagEmoji = "🇦🇺",
                        exchangeRate = 148.50
                    ),
                    Currency(
                        code = "CNY",
                        name = "Chinese Yuan",
                        flagEmoji = "🇨🇳",
                        exchangeRate = 128.00
                    ),
                    Currency(
                        code = "EUR",
                        name = "Euro",
                        flagEmoji = "🇪🇺",
                        exchangeRate = 148.50
                    )
                )
            )
        }
    }
}