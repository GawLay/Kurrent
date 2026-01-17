package test.kyrie.feature.calculator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import test.kyrie.core.data.di.IoDispatcher
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.core.domain.usecase.ConversionCurrencyUseCase
import test.kyrie.core.domain.usecase.ObserveLocalCurrenciesUseCase
import test.kyrie.feature.calculator.model.Currency
import test.kyrie.feature.calculator.model.mapper.toUi
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val observeLocalCurrenciesUseCase: ObserveLocalCurrenciesUseCase,
    private val conversionCurrencyUseCase: ConversionCurrencyUseCase,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    private val _amountFlow = MutableStateFlow("")

    init {
        loadCurrencies()
        observeDebouncedAmount()
    }

    private fun observeDebouncedAmount() {
        viewModelScope.launch(ioDispatcher) {
            _amountFlow
                .debounce(300) // 300ms debounce
                .collect {
                    calculateConversion()
                }
        }
    }

    private fun loadCurrencies() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                observeLocalCurrenciesUseCase()
                    .collect { currencies ->
                        val uiCurrencies = currencies.toUi()

                        if (uiCurrencies.isNotEmpty()) {
                            // Check if there's a saved conversion
                            val savedConversion =
                                conversionCurrencyUseCase.getSavedConversionCurrency()

                            val selectedCurrency = if (savedConversion != null) {
                                // Find the saved currency in the list via currencyCode
                                uiCurrencies.find { it.currencyCode == savedConversion.fromCurrency }
                                    ?: uiCurrencies[0]
                            } else {
                                // Default to first currency
                                uiCurrencies[0]
                            }

                            val initialAmount = savedConversion?.fromAmount ?: "100.00"

                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                currencies = uiCurrencies,
                                selectedCurrency = selectedCurrency,
                                amount = initialAmount,
                                error = null
                            )

                            // Calculate initial conversion
                            calculateConversion()
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load currencies"
                )
            }
        }
    }

    fun onCurrencySelected(currency: Currency) {
        _uiState.value = _uiState.value.copy(selectedCurrency = currency)
        // Immediate calculation for currency change
        viewModelScope.launch(ioDispatcher) {
            calculateConversion()
        }
    }

    fun onAmountChanged(amount: String) {
        // Allow only valid decimal input
        if (amount.isEmpty() || amount.matches(Regex("^\\d*\\.?\\d*$"))) {
            _uiState.value = _uiState.value.copy(amount = amount)
            _amountFlow.value = amount
        }
    }

    private fun calculateConversion() {
        val state = _uiState.value
        val selectedCurrency = state.selectedCurrency ?: return
        val amount = state.amount.toDoubleOrNull() ?: return

        try {
            val rateToUsd = selectedCurrency.rateToUsd.toDouble()

            // Convert from selected currency to USD
            val convertedAmount = if (rateToUsd > 0) {
                amount / rateToUsd
            } else {
                0.0
            }

            val formattedAmount = BigDecimal(convertedAmount)
                .setScale(2, RoundingMode.HALF_UP)
                .toString()

            _uiState.value = _uiState.value.copy(
                convertedAmount = formattedAmount,
                error = null
            )

            saveConversionAfterCalculation(
                fromAmount = amount.toString(),
                fromCurrency = selectedCurrency.currencyCode,
                toAmount = formattedAmount
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                error = "Conversion failed: ${e.message}"
            )
        }
    }


    private fun saveConversionAfterCalculation(
        fromAmount: String,
        fromCurrency: String,
        toAmount: String
    ) {
        viewModelScope.launch(ioDispatcher) {
            val saveConversion = SaveConversionDomain(
                fromAmount = fromAmount,
                fromCurrency = fromCurrency,
                toAmount = toAmount,
                toCurrency = _uiState.value.targetCurrency,
                timestamp = System.currentTimeMillis()
            )

            try {
                conversionCurrencyUseCase.saveConversionCurrency(saveConversion)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to save conversion: ${e.message}"
                )
            }
        }
    }
}
