package test.kyrie.core.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import test.kyrie.core.data.local.dao.CurrencyDao
import test.kyrie.core.data.local.dao.SaveConversionDao
import test.kyrie.core.data.local.entity.CurrencyEntity
import test.kyrie.core.data.local.entity.SaveConversionEntity
import test.kyrie.core.data.local.preferences.CurrencyPreferences
import test.kyrie.core.data.model.ExchangeRatesResponseDto
import test.kyrie.core.data.model.SupportedCurrenciesResponseDto
import test.kyrie.core.data.remote.IKurrentApi
import test.kyrie.core.data.repository.CurrencyRepositoryImpl
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.core.domain.util.Result

/**
 * - API data is fetched correctly
 * - Data is saved into database
 * - Cached data is returned after save
 */
class CurrencyRepositoryTest {

    private lateinit var repository: CurrencyRepositoryImpl
    private lateinit var currencyDao: CurrencyDao
    private lateinit var saveConversionDao: SaveConversionDao
    private lateinit var api: IKurrentApi
    private lateinit var preferences: CurrencyPreferences

    private val testCurrencies = listOf(
        CurrencyEntity(
            code = "EUR",
            name = "Euro",
            countryName = "European Union",
            countryCode = "EU",
            rateToUsd = "0.85",
            iconUrl = "",
            isAvailable = true
        ),
        CurrencyEntity(
            code = "GBP",
            name = "British Pound",
            countryName = "United Kingdom",
            countryCode = "GB",
            rateToUsd = "0.73",
            iconUrl = "",
            isAvailable = true
        )
    )

    @Before
    fun setup() {
        currencyDao = mockk(relaxed = true)
        saveConversionDao = mockk(relaxed = true)
        api = mockk()
        preferences = mockk(relaxed = true)

        repository = CurrencyRepositoryImpl(currencyDao, saveConversionDao, api, preferences)
    }

    @Test
    fun `API data is fetched correctly and saved to database`() = runTest {
        val supportedCurrenciesDto = SupportedCurrenciesResponseDto(
            supportedCurrenciesMap = mapOf(
                "EUR" to test.kyrie.core.data.model.CurrencyDto(
                    currencyCode = "EUR",
                    currencyName = "Euro",
                    countryName = "European Union",
                    countryCode = "EU",
                    status = "available",
                    availableFrom = "2024-01-01",
                    icon = ""
                ),
                "GBP" to test.kyrie.core.data.model.CurrencyDto(
                    currencyCode = "GBP",
                    currencyName = "British Pound",
                    countryName = "United Kingdom",
                    countryCode = "GB",
                    status = "available",
                    availableFrom = "2024-01-01",
                    icon = ""
                )
            )
        )

        val ratesDto = ExchangeRatesResponseDto(
            date = "2024-01-01",
            base = "USD",
            rates = mapOf(
                "EUR" to "0.85",
                "GBP" to "0.73"
            )
        )

        coEvery { api.getSupportedCurrencies() } returns supportedCurrenciesDto
        coEvery { api.getLatestRates() } returns ratesDto
        coEvery { currencyDao.getCurrencyCount() } returns 0
        every { preferences.lastFetchTimestamp } returns 0L
        every { currencyDao.observeAllCurrencies() } returns flowOf(testCurrencies)

        //Fetch currencies (force refresh) and skip Loading, get Success
        var successResult: Result<List<test.kyrie.core.domain.model.CurrencyDomain>>? = null
        repository.getCurrencies(forceRefresh = true).collect { result ->
            if (result is Result.Success) {
                successResult = result
            }
        }

        //Should fetch from API and save to database
        coVerify { api.getSupportedCurrencies() }
        coVerify { api.getLatestRates() }
        coVerify { currencyDao.replaceAllCurrencies(any()) }

        assertNotNull(successResult)
        assertEquals(2, (successResult as Result.Success).data.size)
    }

    @Test
    fun `cached data is returned when cache is valid`() = runTest {
        //Valid cache
        val currentTime = System.currentTimeMillis()
        coEvery { currencyDao.getCurrencyCount() } returns 2
        every { preferences.lastFetchTimestamp } returns currentTime - 60_000L // 1 minute ago
        every { currencyDao.observeAllCurrencies() } returns flowOf(testCurrencies)

        //Fetch currencies without force refresh and collect success result
        var successResult: Result<List<test.kyrie.core.domain.model.CurrencyDomain>>? = null
        repository.getCurrencies(forceRefresh = false).collect { result ->
            if (result is Result.Success) {
                successResult = result
            }
        }

        //Should NOT fetch from API
        coVerify(exactly = 0) { api.getSupportedCurrencies() }
        coVerify(exactly = 0) { api.getLatestRates() }

        assertNotNull(successResult)
        assertEquals(2, (successResult as Result.Success).data.size)
    }

    @Test
    fun `conversion is saved to database`() = runTest {
        val saveConversion = SaveConversionDomain(
            fromAmount = "100.00",
            fromCurrency = "EUR",
            toAmount = "117.65",
            toCurrency = "USD",
            timestamp = System.currentTimeMillis()
        )

        repository.saveConversionCurrency(saveConversion)

        //Should save to database
        coVerify {
            saveConversionDao.replaceSavedConversionCurrency(
                match { entity ->
                    entity.fromAmount == "100.00" &&
                            entity.fromCurrency == "EUR" &&
                            entity.toAmount == "117.65" &&
                            entity.toCurrency == "USD"
                }
            )
        }
    }

    @Test
    fun `saved conversion is retrieved from database`() = runTest {
        //Saved conversion exists
        val savedEntity = SaveConversionEntity(
            fromCurrency = "GBP",
            fromAmount = "200.00",
            toAmount = "273.97",
            toCurrency = "USD",
            timestamp = System.currentTimeMillis()
        )
        coEvery { saveConversionDao.getSavedConversionCurrency() } returns savedEntity

        val result = repository.getSavedConversionCurrency()

        //Should return domain model
        assertEquals("200.00", result?.fromAmount)
        assertEquals("GBP", result?.fromCurrency)
        assertEquals("273.97", result?.toAmount)
    }

    @Test
    fun `returns cached data when API fails`() = runTest {
        //Forced api to fail to trigger catch block
        coEvery { api.getSupportedCurrencies() } throws Exception("Network error")
        //forced clear database to trigger API call
        coEvery { currencyDao.getCurrencyCount() } returns 0
        coEvery { currencyDao.getAllCurrencies() } returns testCurrencies
        every { preferences.lastFetchTimestamp } returns 0L

        //Try to fetch with force refresh and collect success result
        var successResult: Result<List<test.kyrie.core.domain.model.CurrencyDomain>>? = null
        repository.getCurrencies(forceRefresh = true).collect { result ->
            if (result is Result.Success) {
                successResult = result
            }
        }

        //Should return cached data despite API failure
        assertNotNull(successResult)
        assertEquals(2, (successResult as Result.Success).data.size)
    }

    @Test
    fun `local currencies flow returns data from database`() = runTest {
        every { currencyDao.observeAllCurrencies() } returns flowOf(testCurrencies)
        val currencies = repository.getLocalCurrencies().first()

        //Should return mapped domain models
        assertEquals(2, currencies.size)
        assertEquals("EUR", currencies[0].code)
        assertEquals("GBP", currencies[1].code)
    }
}

