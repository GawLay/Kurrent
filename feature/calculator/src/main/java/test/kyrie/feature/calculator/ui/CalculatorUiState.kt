package test.kyrie.feature.calculator.ui

import test.kyrie.feature.calculator.model.Currency

data class CalculatorUiState(
    val isLoading: Boolean = false,
    val currencies: List<Currency> = emptyList(),
    val selectedCurrency: Currency? = null,
    val amount: String = "1000.00",
    val convertedAmount: String = "",
    val targetCurrency: String = "USD",
    val error: String? = null
) {
    val canConvert: Boolean
        get() = selectedCurrency != null && amount.isNotEmpty() && amount.toDoubleOrNull() != null
}
