package test.kyrie.feature.currency_list.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.lifecycle.viewmodel.compose.viewModel
import test.kyrie.core.components.CurrencyItem
import test.kyrie.core.components.KurrentCardView
import test.kyrie.core.components.KurrentFloatingActionButton
import test.kyrie.core.components.QuickLookCard
import test.kyrie.core.components.SectionHeader
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.core.theme.dimensions


/**
 * Currency List Screen - Main entry point of the app
 * Displays list of currencies with exchange rates and a quick look card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    viewModel: CurrencyListViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToCalculator: () -> Unit = {},
    onCurrencyClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()


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
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
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
            modifier = Modifier.padding(paddingValues)
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
                        availableCurrencies = uiState.availableCurrencies,
                    )
                }


                // Currency List
                items(
                    items = uiState.currencies,
                    key = { currency -> currency.code }
                ) { currency ->
                    KurrentCardView {
                        CurrencyItem(
                            currencyCode = currency.code,
                            exchangeRate = String.format("%.2f", currency.exchangeRate),
                            flagEmoji = currency.flagEmoji,
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
        CurrencyListScreen()
    }
}


@Preview(showBackground = true)
@Composable
private fun CurrencyListScreenLightPreview() {
    KurrentTheme(darkTheme = false) {
        CurrencyListScreen()
    }
}