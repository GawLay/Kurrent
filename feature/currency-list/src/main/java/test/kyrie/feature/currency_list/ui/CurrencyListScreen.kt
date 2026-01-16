package test.kyrie.feature.currency_list.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import test.kyrie.core.components.CurrencyItem
import test.kyrie.core.components.KurrentCardView
import test.kyrie.core.components.KurrentFloatingActionButton
import test.kyrie.core.components.QuickLookCard
import test.kyrie.core.components.SectionHeader
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.core.theme.dimensions
import test.kyrie.feature.currency_list.model.Currency
import test.kyrie.feature.currency_list.model.SavedConversion
import test.kyrie.feature.util.FeatureCurrencyConstants


/**
 * Displays list of currencies with exchange rates and a quick look card
 * - Offline-first data loading
 * - Manual refresh functionality via refresh button
 * - Timer-based cache refresh (5 minutes)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    viewModel: CurrencyListViewModel = hiltViewModel(),
    onNavigateToCalculator: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    CurrencyListScreenContent(
        uiState = uiState,
        onRefresh = { viewModel.refreshCurrencies() },
        onNavigateToCalculator = onNavigateToCalculator
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyListScreenContent(
    uiState: CurrencyListUiState,
    onRefresh: () -> Unit,
    onNavigateToCalculator: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Main View",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = onRefresh,
                        enabled = !uiState.isRefreshing
                    ) {
                        if (uiState.isRefreshing) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(MaterialTheme.dimensions.paddingXs)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh currencies"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        floatingActionButton = {
            KurrentFloatingActionButton(
                onClick = onNavigateToCalculator
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Open calculator"
                )
            }
        }
    ) { paddingValues ->
        CurrencyListContent(
            uiState = uiState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}


@Composable
private fun CurrencyListContent(
    uiState: CurrencyListUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.dimensions.screenPaddingHorizontal,
                    vertical = MaterialTheme.dimensions.screenPaddingVertical
                ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd)
            ) {
                // Quick Look Card
                uiState.savedConversion?.let { conversion ->
                    item {
                        QuickLookCard(
                            fromAmount = conversion.fromAmount,
                            fromCurrency = conversion.fromCurrency,
                            toAmount = conversion.toAmount,
                            toCurrency = conversion.toCurrency
                        )
                    }
                }

                // Section Header
                item {
                    SectionHeader(
                        modifier = Modifier.padding(
                            top = MaterialTheme.dimensions.spacingSm
                        ),
                        title = "All Currencies",
                        currencyBaseTitle = uiState.baseCurrency,
                    )
                }

                // Currency List
                items(
                    items = uiState.currencies,
                    key = { currency -> currency.currencyCode }
                ) { currency ->
                    KurrentCardView {
                        CurrencyItem(
                            currencyCode = currency.currencyCode,
                            exchangeRate = currency.rateToUsd,
                            iconUrl = currency.iconUrl,
                        )
                    }
                }
            }
        }


        // Error message
        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(MaterialTheme.dimensions.paddingLg)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CurrencyListScreenPreview() {
    KurrentTheme(darkTheme = true) {
        CurrencyListScreenContent(
            uiState = CurrencyListUiState(
                currencies = listOf(
                    Currency(
                        currencyCode = "JPY",
                        currencyName = "Japanese Yen",
                        countryName = "Japan",
                        countryCode = "JP",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "148.50",
                        isAvailable = true
                    ),
                    Currency(
                        currencyCode = "EUR",
                        currencyName = "Euro",
                        countryName = "European Union",
                        countryCode = "EU",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "1.10",
                        isAvailable = true
                    ),
                    Currency(
                        currencyCode = "GBP",
                        currencyName = "British Pound",
                        countryName = "United Kingdom",
                        countryCode = "GB",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "1.25",
                        isAvailable = true
                    ),
                ),
                baseCurrency = "USD",
                isLoading = false,
                isRefreshing = false,
                error = null,
                savedConversion = SavedConversion(
                    fromAmount = "100.0",
                    fromCurrency = "EUR",
                    toAmount = "117.65",
                    toCurrency = "USD"
                )
            ),
            onRefresh = {},
            onNavigateToCalculator = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun CurrencyListScreenLoadingPreview() {
    KurrentTheme(darkTheme = false) {
        CurrencyListScreenContent(
            uiState = CurrencyListUiState(
                currencies = emptyList(),
                baseCurrency = "USD",
                isLoading = true,
                isRefreshing = false,
                error = null
            ),
            onRefresh = {},
            onNavigateToCalculator = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun CurrencyListScreenErrorPreview() {
    KurrentTheme(darkTheme = false) {
        CurrencyListScreenContent(
            uiState = CurrencyListUiState(
                currencies = emptyList(),
                baseCurrency = "USD",
                isLoading = false,
                isRefreshing = false,
                error = "Failed to load currencies"
            ),
            onRefresh = {},
            onNavigateToCalculator = {}
        )
    }
}