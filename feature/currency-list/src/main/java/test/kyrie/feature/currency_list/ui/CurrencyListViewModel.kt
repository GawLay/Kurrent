package test.kyrie.feature.currency_list.ui


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CurrencyListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CurrencyListUiState.mockData())
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()


    /**
     * Navigate to calculator screen
     */
    fun onCalculatorClick() {
        // Navigation logic will be handled by the screen
    }



    /**
     * Refresh currency data
     */
    fun refreshCurrencies() {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )

        // Simulate data loading
        // In real implementation, this would call a repository
        _uiState.value = _uiState.value.copy(
            isLoading = false
        )
    }
}
