package test.kyrie.core.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import test.kyrie.core.data.local.dao.CurrencyDao
import test.kyrie.core.data.mapper.toDomain
import test.kyrie.core.data.mapper.toEntity
import test.kyrie.core.data.remote.IKurrentApi
import test.kyrie.core.domain.model.Currency
import test.kyrie.core.domain.repository.CurrencyRepository
import test.kyrie.core.domain.util.Result
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val kurrentApi: IKurrentApi
) : CurrencyRepository {

    override suspend fun getCurrencies(forceRefresh: Boolean): Flow<Result<List<Currency>>> = flow {
        emit(Result.Loading)

        try {
            // Check if we need to refresh
            val shouldRefresh = forceRefresh || currencyDao.getCurrencyCount() == 0

            if (shouldRefresh) {
                // Fetch from remote and save to local
                fetchAndCacheCurrencies()
            }
            // Emit data from local database
            emitAll(
                currencyDao.observeAllCurrencies()
                    .map { Result.Success(it.toDomain()) }
            )

//            currencyDao.observeAllCurrencies()
//                .map { entities ->
//                    Result.Success(entities.toDomain())
//                }
//                .collect { emit(it) }

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

    override fun getLocalCurrencies(): Flow<List<Currency>> {
        return currencyDao.observeAllCurrencies()
            .map { entities -> entities.toDomain() }
    }

    override suspend fun refreshCurrencies(): Result<Unit> {
        return try {
            fetchAndCacheCurrencies()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, "Failed to refresh currencies: ${e.message}")
        }
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

