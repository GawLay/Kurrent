package test.kyrie.feature.currency_list.ui


import test.kyrie.feature.currency_list.model.Currency
import test.kyrie.feature.currency_list.model.SavedConversion
import test.kyrie.feature.util.FeatureCurrencyConstants


data class CurrencyListUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val savedConversion: SavedConversion? = null,
    val currencies: List<Currency> = emptyList(),
    val selectedCurrency: String? = null,
    val baseCurrency: String = "USD",
    val availableCurrencies: Map<String, String> = emptyMap(),
    val error: String? = null
) {
    companion object {
        /**
         *  mock data for preview
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
                currencies = listOf(
                    Currency(
                        currencyCode = "JPY",
                        currencyName = "Japanese Yen",
                        countryName = "Japan",
                        countryCode = "JP",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "148.50",
                        isAvailable = true
                    ),
                    Currency(
                        currencyCode = "EUR",
                        currencyName = "Euro",
                        countryName = "European Union",
                        countryCode = "EU",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "1.10",
                        isAvailable = true
                    ),
                    Currency(
                        currencyCode = "GBP",
                        currencyName = "British Pound",
                        countryName = "United Kingdom",
                        countryCode = "GB",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "1.25",
                        isAvailable = true
                    ),
                    Currency(
                        currencyCode = "AUD",
                        currencyName = "Australian Dollar",
                        countryName = "Australia",
                        countryCode = "AU",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "0.70",
                        isAvailable = true
                    )
                )
            )
        }
    }
}