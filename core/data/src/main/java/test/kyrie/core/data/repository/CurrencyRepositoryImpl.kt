package test.kyrie.core.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import test.kyrie.core.data.local.dao.CurrencyDao
import test.kyrie.core.data.local.dao.SaveConversionDao
import test.kyrie.core.data.local.preferences.CurrencyPreferences
import test.kyrie.core.data.mapper.toDomain
import test.kyrie.core.data.mapper.toEntity
import test.kyrie.core.data.remote.IKurrentApi
import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.core.domain.repository.CurrencyRepository
import test.kyrie.core.domain.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val saveConversionDao: SaveConversionDao,
    private val kurrentApi: IKurrentApi,
    private val currencyPreferences: CurrencyPreferences
) : CurrencyRepository {

    // Cache validity duration: 5 minutes (300,000 ms)
    private val cacheValidityDuration = 5 * 60 * 1000L // 5 minutes

    private fun isCacheValid(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastFetchTimestamp = currencyPreferences.lastFetchTimestamp
        return (currentTime - lastFetchTimestamp) < cacheValidityDuration
    }

    override suspend fun getCurrencies(forceRefresh: Boolean): Flow<Result<List<CurrencyDomain>>> =
        flow {
            emit(Result.Loading)

            try {
                // Check if we need to refresh based on:
                // 1. Force refresh flag
                // 2. Empty database
                // 3. Cache expiration (5 minutes)
                val shouldRefresh = forceRefresh ||
                        currencyDao.getCurrencyCount() == 0 ||
                        !isCacheValid()

                if (shouldRefresh) {
                    // Fetch from remote and save to local
                    fetchAndCacheCurrencies()
                    currencyPreferences.lastFetchTimestamp = System.currentTimeMillis()
                }

                emitAll(
                    currencyDao.observeAllCurrencies()
                        .map { Result.Success(it.toDomain()) }
                )


            } catch (e: Exception) {
                // If remote fetch fails but we have cached data, emit cached data
                val cachedData = currencyDao.getAllCurrencies()
                if (cachedData.isNotEmpty()) {
                    emit(Result.Success(cachedData.toDomain()))
                } else {
                    emit(Result.Error(e, "Failed to fetch currencies: ${e.message}"))
                }
            }
        }

    override fun getLocalCurrencies(): Flow<List<CurrencyDomain>> {
        return currencyDao.observeAllCurrencies()
            .map { entities -> entities.toDomain() }
    }

    override suspend fun refreshCurrencies(): Result<Unit> {
        return try {
            fetchAndCacheCurrencies()
            currencyPreferences.lastFetchTimestamp = System.currentTimeMillis()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, "Failed to refresh currencies: ${e.message}")
        }
    }

    override suspend fun saveConversionCurrency(saveConversionDomain: SaveConversionDomain) {
        saveConversionDao.insertSavedConversionCurrency(saveConversionDomain.toEntity())
    }

    override suspend fun getSavedConversionCurrency(): SaveConversionDomain? {
        val entity = saveConversionDao.getSavedConversionCurrency()
        return entity?.toDomain()
    }


    /**
     * Main fetcher.
     */
    private suspend fun fetchAndCacheCurrencies() = coroutineScope {
        // Call both APIs in parallel
        val supportedCurrenciesDeferred = async { kurrentApi.getSupportedCurrencies() }
        val latestRatesDeferred = async { kurrentApi.getLatestRates() }

        // Waiting for both to complete
        //response example --> "CurrencyCode":{"rate"},....
        val supportedCurrenciesResponse = supportedCurrenciesDeferred.await()

        //response example --> "base:"USD","rates":{"CurrencyCode":rate,...},...
        val latestRatesResponse = latestRatesDeferred.await()

        // Combine the data because we need to display icon.
        val currencyEntities =
            supportedCurrenciesResponse.supportedCurrenciesMap.values.map { currencyDto ->
                val rate = latestRatesResponse.rates[currencyDto.currencyCode] ?: "0"
                currencyDto.toEntity(rate)
            }

        // Save to database
        currencyDao.replaceAllCurrencies(currencyEntities)
    }
}

