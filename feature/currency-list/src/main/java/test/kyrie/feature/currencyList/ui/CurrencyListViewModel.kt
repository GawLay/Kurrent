package test.kyrie.feature.currencyList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import test.kyrie.core.data.di.IoDispatcher
import test.kyrie.core.domain.usecase.ConversionCurrencyUseCase
import test.kyrie.core.domain.usecase.GetCurrenciesUseCase
import test.kyrie.core.domain.usecase.RefreshCurrenciesUseCase
import test.kyrie.core.domain.util.onError
import test.kyrie.core.domain.util.onLoading
import test.kyrie.core.domain.util.onSuccess
import test.kyrie.feature.currencyList.model.mapper.toUi
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel
    @Inject
    constructor(
        private val getCurrenciesUseCase: GetCurrenciesUseCase,
        private val conversionCurrencyUseCase: ConversionCurrencyUseCase,
        private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase,
        @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CurrencyListUiState())
        val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()

        init {
            // Load currencies on initialization (offline-first)
            getCurrencies(forceRefresh = false)
            // Observe saved conversion from calculator
            observeConversionCurrency()
        }

        /**
         * Fetch currencies from repository
         * @param forceRefresh - if true, forces a network call
         * otherwise uses cache/timer strategy
         */
        fun getCurrencies(forceRefresh: Boolean = false) {
            viewModelScope.launch(ioDispatcher) {
                getCurrenciesUseCase(forceRefresh)
                    .catch { exception ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "Unknown error occurred",
                            )
                    }.collect { result ->
                        result
                            .onLoading {
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = true,
                                        error = null,
                                    )
                            }.onSuccess { currencies ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        currencies = currencies.toUi(),
                                        error = null,
                                    )
                            }.onError { exception, message ->
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        error = message ?: exception.message ?: "Failed to load currencies",
                                    )
                            }
                    }
            }
        }

        /**
         * This always forces a network call
         */
        fun refreshCurrencies() {
            viewModelScope.launch(ioDispatcher) {
                _uiState.value =
                    _uiState.value.copy(
                        isRefreshing = true,
                        error = null,
                    )

                val result = refreshCurrenciesUseCase()

                result
                    .onSuccess {
                        // After successful refresh, get currencies again
                        // This will emit from the database with updated data
                        getCurrencies(forceRefresh = false)
                        _uiState.value =
                            _uiState.value.copy(
                                isRefreshing = false,
                            )
                    }.onError { exception, message ->
                        _uiState.value =
                            _uiState.value.copy(
                                isRefreshing = false,
                                error = message ?: exception.message ?: "Failed to refresh currencies",
                            )
                    }
            }
        }

        /**
         * Observe the saved conversion currency for the Quick look data
         */
        private fun observeConversionCurrency() {
            viewModelScope.launch(ioDispatcher) {
                conversionCurrencyUseCase
                    .observeSavedConversionCurrency()
                    .collect { conversionCurrency ->
                        _uiState.value =
                            _uiState.value.copy(
                                savedConversion = conversionCurrency?.toUi(),
                            )
                    }
            }
        }
    }
