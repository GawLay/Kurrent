package test.kyrie.feature.calculator

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.model.SaveConversionDomain
import test.kyrie.core.domain.usecase.ConversionCurrencyUseCase
import test.kyrie.core.domain.usecase.ObserveLocalCurrenciesUseCase
import test.kyrie.feature.calculator.ui.CalculatorViewModel

/**
 * - Saved conversion updates after calculation
 * - UI state changes on success/failure
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorViewModelTest {

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var observeLocalCurrenciesUseCase: ObserveLocalCurrenciesUseCase
    private lateinit var conversionCurrencyUseCase: ConversionCurrencyUseCase
    private val testDispatcher = StandardTestDispatcher()

    //dummy
    private val testCurrencies = listOf(
        CurrencyDomain(
            code = "EUR",
            name = "Euro",
            countryName = "European Union",
            countryCode = "EU",
            rateToUsd = "0.85",
            iconUrl = "",
            isAvailable = true
        ),
        CurrencyDomain(
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
        Dispatchers.setMain(testDispatcher)
        observeLocalCurrenciesUseCase = mockk()
        conversionCurrencyUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `currency conversion calculates correctly`() = runTest {
        coEvery { observeLocalCurrenciesUseCase() } returns flowOf(testCurrencies)
        coEvery { conversionCurrencyUseCase.getSavedConversionCurrency() } returns null

        viewModel = CalculatorViewModel(
            observeLocalCurrenciesUseCase,
            conversionCurrencyUseCase,
            testDispatcher
        )
        advanceUntilIdle()

        //Convert 100 EUR to USD (rate 0.85)
        viewModel.onAmountChanged("100")
        advanceUntilIdle()

        //Should convert correctly (100 / 0.85 = 117.65)
        val state = viewModel.uiState.value
        assertEquals("117.65", state.convertedAmount)
        assertNull(state.error)
    }

    @Test
    fun `saved conversion is updated after calculation`() = runTest {
        coEvery { observeLocalCurrenciesUseCase() } returns flowOf(testCurrencies)
        coEvery { conversionCurrencyUseCase.getSavedConversionCurrency() } returns null

        viewModel = CalculatorViewModel(
            observeLocalCurrenciesUseCase,
            conversionCurrencyUseCase,
            testDispatcher
        )
        advanceUntilIdle()

        //User enters amount and calculates
        viewModel.onAmountChanged("250")
        advanceUntilIdle()

        //Saved conversion should be called
        coVerify {
            conversionCurrencyUseCase.saveConversionCurrency(
                match { conversion ->
                    conversion.fromAmount == "250.0" &&
                            conversion.fromCurrency == "EUR" &&
                            conversion.toCurrency == "USD"
                }
            )
        }
    }

    @Test
    fun `UI state shows error on conversion failure`() = runTest {
        val invalidCurrency = CurrencyDomain(
            code = "XXX",
            name = "Invalid",
            countryName = "Invalid",
            countryCode = "XX",
            rateToUsd = "invalid",
            iconUrl = "",
            isAvailable = true
        )
        coEvery { observeLocalCurrenciesUseCase() } returns flowOf(listOf(invalidCurrency))
        coEvery { conversionCurrencyUseCase.getSavedConversionCurrency() } returns null

        viewModel = CalculatorViewModel(
            observeLocalCurrenciesUseCase,
            conversionCurrencyUseCase,
            testDispatcher
        )
        advanceUntilIdle()

        //Try to convert with invalid rate
        viewModel.onAmountChanged("100")
        advanceUntilIdle()

        //Should have error in state
        val state = viewModel.uiState.value
        assertNotNull(state.error)
    }

    @Test
    fun `UI state updates when currency is changed`() = runTest {
        coEvery { observeLocalCurrenciesUseCase() } returns flowOf(testCurrencies)
        coEvery { conversionCurrencyUseCase.getSavedConversionCurrency() } returns null

        viewModel = CalculatorViewModel(
            observeLocalCurrenciesUseCase,
            conversionCurrencyUseCase,
            testDispatcher
        )
        advanceUntilIdle()

        //User selects different currency
        val gbpCurrency = testCurrencies[1]
        viewModel.onCurrencySelected(
            test.kyrie.feature.calculator.model.Currency(
                currencyCode = gbpCurrency.code,
                currencyName = gbpCurrency.name,
                countryName = gbpCurrency.countryName,
                countryCode = gbpCurrency.countryCode,
                rateToUsd = gbpCurrency.rateToUsd,
                iconUrl = gbpCurrency.iconUrl,
                isAvailable = gbpCurrency.isAvailable
            )
        )
        advanceUntilIdle()

        //Selected currency should update
        val state = viewModel.uiState.value
        assertEquals("GBP", state.selectedCurrency?.currencyCode)
    }

    @Test
    fun `loads saved conversion on initialization`() = runTest {
        val savedConversion = SaveConversionDomain(
            fromAmount = "500.00",
            fromCurrency = "EUR",
            toAmount = "588.24",
            toCurrency = "USD",
            timestamp = System.currentTimeMillis()
        )
        coEvery { observeLocalCurrenciesUseCase() } returns flowOf(testCurrencies)
        coEvery { conversionCurrencyUseCase.getSavedConversionCurrency() } returns savedConversion

        viewModel = CalculatorViewModel(
            observeLocalCurrenciesUseCase,
            conversionCurrencyUseCase,
            testDispatcher
        )
        advanceUntilIdle()

        //Should load saved amount and currency
        val state = viewModel.uiState.value
        assertEquals("500.00", state.amount)
        assertEquals("EUR", state.selectedCurrency?.currencyCode)
    }
}

