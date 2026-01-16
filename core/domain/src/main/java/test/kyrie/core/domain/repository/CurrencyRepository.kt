package test.kyrie.core.domain.repository

import kotlinx.coroutines.flow.Flow
import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.core.domain.util.Result

interface CurrencyRepository {

    /**
     * Fetches all currencies by calling both getSupportedCurrencies
     * and getLatestRates in parallel,.
     * combines them, and caches in local database. viola
     */
    suspend fun getCurrencies(forceRefresh: Boolean = false): Flow<Result<List<CurrencyDomain>>>

    fun getLocalCurrencies(): Flow<List<CurrencyDomain>>

    suspend fun refreshCurrencies(): Result<Unit>

    suspend fun saveConversionCurrency(saveConversionDomain: SaveConversionDomain)
    suspend fun getSavedConversionCurrency(): SaveConversionDomain?
}

