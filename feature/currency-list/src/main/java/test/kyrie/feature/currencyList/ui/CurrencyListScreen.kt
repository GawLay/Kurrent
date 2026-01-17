package test.kyrie.feature.currencyList.ui

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
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.EntryPointAccessors
import test.kyrie.core.components.CurrencyItem
import test.kyrie.core.components.KurrentCardView
import test.kyrie.core.components.KurrentFloatingActionButton
import test.kyrie.core.components.QuickLookCard
import test.kyrie.core.components.SectionHeader
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.core.theme.KurrentTypography
import test.kyrie.core.theme.dimensions
import test.kyrie.core.theme.manager.ThemeManagerEntryPoint
import test.kyrie.core.theme.manager.ThemeMode
import test.kyrie.core.theme.manager.observeThemeMode
import test.kyrie.feature.currencyList.model.Currency
import test.kyrie.feature.currencyList.model.SavedConversion
import test.kyrie.feature.util.FeatureCurrencyConstants

/**
 * - Offline-first data loading
 * - Manual refresh functionality via refresh button
 * - Timer-based cache refresh (5 minutes)
 * - Theme toggle for switching between dark and light modes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    viewModel: CurrencyListViewModel = hiltViewModel(),
    onNavigateToCalculator: () -> Unit = {},
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val themeManager =
        remember {
            EntryPointAccessors
                .fromApplication(
                    context.applicationContext,
                    ThemeManagerEntryPoint::class.java,
                ).themeManager()
        }

    val uiState by viewModel.uiState.collectAsState()
    val themeMode = themeManager.observeThemeMode()

    CurrencyListScreenContent(
        uiState = uiState,
        themeMode = themeMode,
        onRefresh = { viewModel.refreshCurrencies() },
        onThemeToggle = { themeManager.toggleTheme() },
        onNavigateToCalculator = onNavigateToCalculator,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyListScreenContent(
    uiState: CurrencyListUiState,
    themeMode: ThemeMode,
    onRefresh: () -> Unit,
    onThemeToggle: () -> Unit,
    onNavigateToCalculator: () -> Unit,
) {
    Scaffold(
        topBar = {
            CurrencyListTopBar(
                themeMode = themeMode,
                isRefreshing = uiState.isRefreshing,
                onThemeToggle = onThemeToggle,
                onRefresh = onRefresh,
            )
        },
        floatingActionButton = {
            KurrentFloatingActionButton(
                onClick = onNavigateToCalculator,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Open calculator",
                )
            }
        },
    ) { paddingValues ->
        CurrencyListContent(
            uiState = uiState,
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
        )
    }
}

@Composable
private fun CurrencyListContent(
    uiState: CurrencyListUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            uiState.error != null -> {
                ErrorMessage(
                    error = uiState.error,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            else -> {
                CurrencyList(
                    currencies = uiState.currencies,
                    savedConversion = uiState.savedConversion,
                    baseCurrency = uiState.baseCurrency,
                )
            }
        }
    }
}

@Composable
private fun CurrencyList(
    currencies: List<Currency>,
    savedConversion: SavedConversion?,
    baseCurrency: String,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding =
            PaddingValues(
                horizontal = MaterialTheme.dimensions.screenPaddingHorizontal,
                vertical = MaterialTheme.dimensions.screenPaddingVertical,
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd),
    ) {
        // Quick Look Card
        savedConversion?.let { conversion ->
            item {
                QuickLookCard(
                    fromAmount = conversion.fromAmount,
                    fromCurrency = conversion.fromCurrency,
                    toAmount = conversion.toAmount,
                    toCurrency = conversion.toCurrency,
                )
            }
        }

        // Section Header
        item {
            SectionHeader(
                title = "All Currencies",
                currencyBaseTitle = baseCurrency,
                modifier =
                    Modifier.padding(
                        top = MaterialTheme.dimensions.spacingSm,
                    ),
            )
        }

        // Currency List
        items(
            items = currencies,
            key = { currency -> currency.currencyCode },
        ) { currency ->
            CurrencyListItem(currency = currency)
        }
    }
}

@Composable
private fun CurrencyListItem(currency: Currency) {
    KurrentCardView {
        CurrencyItem(
            currencyCode = currency.currencyCode,
            exchangeRate = currency.rateToUsd,
            iconUrl = currency.iconUrl,
        )
    }
}

@Composable
private fun ErrorMessage(
    error: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        modifier =
            modifier
                .padding(MaterialTheme.dimensions.paddingLg),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyListTopBar(
    themeMode: ThemeMode,
    isRefreshing: Boolean,
    onThemeToggle: () -> Unit,
    onRefresh: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Main View",
                style =
                    KurrentTypography.titleLarge.copy(
                        fontWeight = FontWeight.Medium,
                    ),
            )
        },
        actions = {
            // Theme toggle button
            IconButton(
                onClick = onThemeToggle,
            ) {
                Icon(
                    imageVector =
                        if (themeMode == ThemeMode.DARK) {
                            Icons.Outlined.LightMode
                        } else {
                            Icons.Outlined.DarkMode
                        },
                    contentDescription = "Toggle theme",
                )
            }

            // Refresh button
            IconButton(
                onClick = onRefresh,
                enabled = !isRefreshing,
            ) {
                if (isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(MaterialTheme.dimensions.paddingXs),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh currencies",
                    )
                }
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun CurrencyListScreenPreview() {
    KurrentTheme(darkTheme = true) {
        CurrencyListScreenContent(
            uiState =
                CurrencyListUiState(
                    currencies =
                        listOf(
                            Currency(
                                currencyCode = "JPY",
                                currencyName = "Japanese Yen",
                                countryName = "Japan",
                                countryCode = "JP",
                                iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                                rateToUsd = "148.50",
                                isAvailable = true,
                            ),
                            Currency(
                                currencyCode = "EUR",
                                currencyName = "Euro",
                                countryName = "European Union",
                                countryCode = "EU",
                                iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                                rateToUsd = "1.10",
                                isAvailable = true,
                            ),
                            Currency(
                                currencyCode = "GBP",
                                currencyName = "British Pound",
                                countryName = "United Kingdom",
                                countryCode = "GB",
                                iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                                rateToUsd = "1.25",
                                isAvailable = true,
                            ),
                        ),
                    baseCurrency = "USD",
                    isLoading = false,
                    isRefreshing = false,
                    error = null,
                    savedConversion =
                        SavedConversion(
                            fromAmount = "100.0",
                            fromCurrency = "EUR",
                            toAmount = "117.65",
                            toCurrency = "USD",
                        ),
                ),
            themeMode = ThemeMode.DARK,
            onRefresh = {},
            onThemeToggle = {},
            onNavigateToCalculator = {},
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun CurrencyListScreenLoadingPreview() {
    KurrentTheme(darkTheme = false) {
        CurrencyListScreenContent(
            uiState =
                CurrencyListUiState(
                    currencies = emptyList(),
                    baseCurrency = "USD",
                    isLoading = true,
                    isRefreshing = false,
                    error = null,
                ),
            themeMode = ThemeMode.LIGHT,
            onRefresh = {},
            onThemeToggle = {},
            onNavigateToCalculator = {},
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun CurrencyListScreenErrorPreview() {
    KurrentTheme(darkTheme = false) {
        CurrencyListScreenContent(
            uiState =
                CurrencyListUiState(
                    currencies = emptyList(),
                    baseCurrency = "USD",
                    isLoading = false,
                    isRefreshing = false,
                    error = "Failed to load currencies",
                ),
            themeMode = ThemeMode.LIGHT,
            onRefresh = {},
            onThemeToggle = {},
            onNavigateToCalculator = {},
        )
    }
}

@Preview(showBackground = true, name = "Currency List Item")
@Composable
private fun CurrencyListItemPreview() {
    KurrentTheme(darkTheme = true) {
        CurrencyListItem(
            currency =
                Currency(
                    currencyCode = "EUR",
                    currencyName = "Euro",
                    countryName = "European Union",
                    countryCode = "EU",
                    iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                    rateToUsd = "1.10",
                    isAvailable = true,
                ),
        )
    }
}

@Preview(showBackground = true, name = "Currency List with Quick Look")
@Composable
private fun CurrencyListWithQuickLookPreview() {
    KurrentTheme(darkTheme = true) {
        CurrencyList(
            currencies =
                listOf(
                    Currency(
                        currencyCode = "JPY",
                        currencyName = "Japanese Yen",
                        countryName = "Japan",
                        countryCode = "JP",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "148.50",
                        isAvailable = true,
                    ),
                    Currency(
                        currencyCode = "EUR",
                        currencyName = "Euro",
                        countryName = "European Union",
                        countryCode = "EU",
                        iconUrl = FeatureCurrencyConstants.DEFAULT_ICON_URL.defaultValue,
                        rateToUsd = "1.10",
                        isAvailable = true,
                    ),
                ),
            savedConversion =
                SavedConversion(
                    fromAmount = "100.0",
                    fromCurrency = "EUR",
                    toAmount = "117.65",
                    toCurrency = "USD",
                ),
            baseCurrency = "USD",
        )
    }
}

@Preview(showBackground = true, name = "Error Message")
@Composable
private fun ErrorMessagePreview() {
    KurrentTheme(darkTheme = false) {
        ErrorMessage(error = "Failed to load currencies")
    }
}
