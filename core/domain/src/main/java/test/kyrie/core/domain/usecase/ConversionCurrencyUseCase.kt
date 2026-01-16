package test.kyrie.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.core.domain.repository.CurrencyRepository
import javax.inject.Inject

class ConversionCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend fun saveConversionCurrency(saveConversionDomain: SaveConversionDomain) {
        return currencyRepository.saveConversionCurrency(saveConversionDomain)
    }

    suspend fun getSavedConversionCurrency(): SaveConversionDomain? {
        return currencyRepository.getSavedConversionCurrency()
    }

    fun observeSavedConversionCurrency(): Flow<SaveConversionDomain?> {
        return currencyRepository.observeSavedConversionCurrency()
    }
}