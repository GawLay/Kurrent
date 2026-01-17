package test.kyrie.feature.calculator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import test.kyrie.core.components.KurrentCardView
import test.kyrie.core.theme.KurrentTextStyles
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.core.theme.KurrentTypography
import test.kyrie.core.theme.dimensions
import test.kyrie.feature.calculator.model.Currency
import java.util.Locale

/**
 * - Target currency is always USD
 * - Conversion results are saved to DB for quick look
 * - On first launch, displays index 0 currency
 * - If saved conversion exists, displays that currency
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    CalculatorScreenContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onCurrencySelect = viewModel::onCurrencySelected,
        onAmountChange = viewModel::onAmountChange,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculatorScreenContent(
    uiState: CalculatorUiState,
    onNavigateBack: () -> Unit,
    onCurrencySelect: (Currency) -> Unit,
    onAmountChange: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Currency Calculator",
                        style =
                            KurrentTypography.titleLarge.copy(
                                fontWeight = FontWeight.Medium,
                            ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back",
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                horizontal = MaterialTheme.dimensions.screenPaddingHorizontal,
                                vertical = MaterialTheme.dimensions.screenPaddingVertical,
                            ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingLg),
                ) {
                    // Amount Input Section
                    AmountInputCard(
                        selectedCurrency = uiState.selectedCurrency,
                        currencies = uiState.currencies,
                        amount = uiState.amount,
                        onCurrencySelect = onCurrencySelect,
                        onAmountChange = onAmountChange,
                    )

                    // Conversion Result Section
                    ConversionResultCard(
                        targetCurrency = uiState.targetCurrency,
                        convertedAmount = uiState.convertedAmount,
                    )

                    // Exchange Rate Info
                    uiState.selectedCurrency?.let { currency ->
                        ExchangeRateInfo(
                            currency = currency,
                            targetCurrency = uiState.targetCurrency,
                        )
                    }

                    // Error message
                    uiState.error?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(MaterialTheme.dimensions.paddingMd),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AmountInputCard(
    selectedCurrency: Currency?,
    currencies: List<Currency>,
    amount: String,
    onCurrencySelect: (Currency) -> Unit,
    onAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    KurrentCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimensions.cardPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd),
        ) {
            Text(
                text = "Amount",
                style = KurrentTextStyles.sectionTitle,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )

            // Currency Selector
            selectedCurrency?.let { currency ->
                CurrencySelector(
                    selectedCurrency = currency,
                    currencies = currencies,
                    onCurrencySelect = onCurrencySelect,
                )
            }

            // Amount Input
            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Enter amount") },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                    ),
                singleLine = true,
                textStyle = KurrentTextStyles.inputAmount,
            )
        }
    }
}

@Composable
private fun ConversionResultCard(
    targetCurrency: String,
    convertedAmount: String,
    modifier: Modifier = Modifier,
) {
    KurrentCardView(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimensions.cardPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd),
        ) {
            Text(
                text = "Converted Amount",
                style = KurrentTextStyles.sectionTitle,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        MaterialTheme.dimensions.spacingMd,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // USD Flag/Icon - Placeholder
                Text(
                    text = "🇺🇸",
                    style = KurrentTypography.displaySmall,
                    modifier = Modifier.size(48.dp),
                )

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = targetCurrency,
                        style = KurrentTextStyles.currencyCode,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = "United States Dollar",
                        style = KurrentTextStyles.currencyName,
                        color =
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.7f,
                            ),
                    )
                }
            }

            Text(
                text =
                    convertedAmount.ifEmpty {
                        "0.00"
                    },
                style = KurrentTextStyles.conversionResult,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun ExchangeRateInfo(
    currency: Currency,
    targetCurrency: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Indicative Exchange Rate\n1 ${currency.currencyCode} = ${
            if (currency.rateToUsd.toDoubleOrNull() != null) {
                String.format(
                    Locale.ENGLISH,
                    "%.4f",
                    1 / currency.rateToUsd.toDouble(),
                )
            } else {
                "0"
            }
        } $targetCurrency",
        style = KurrentTypography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun CurrencySelector(
    selectedCurrency: Currency,
    currencies: List<Currency>,
    onCurrencySelect: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        TextButton(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Currency Icon
                    AsyncImage(
                        model = selectedCurrency.iconUrl,
                        contentDescription = selectedCurrency.currencyName,
                        modifier = Modifier.size(40.dp),
                    )

                    Column {
                        Text(
                            text = selectedCurrency.currencyCode,
                            style =
                                KurrentTypography.titleLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                ),
                        )
                        Text(
                            text = selectedCurrency.currencyName,
                            style = KurrentTypography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select currency",
                )
            }
        }

        if (showDialog) {
            CurrencyPickerDialog(
                currencies = currencies,
                onCurrencySelect = { currency ->
                    onCurrencySelect(currency)
                    showDialog = false
                },
                onDismiss = { showDialog = false },
            )
        }
    }
}

@Composable
private fun CurrencyPickerDialog(
    currencies: List<Currency>,
    onCurrencySelect: (Currency) -> Unit,
    onDismiss: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredCurrencies =
        remember(searchQuery, currencies) {
            if (searchQuery.isBlank()) {
                currencies
            } else {
                currencies.filter { currency ->
                    currency.currencyCode.contains(searchQuery, ignoreCase = true) ||
                        currency.currencyName.contains(searchQuery, ignoreCase = true) ||
                        currency.countryName.contains(searchQuery, ignoreCase = true)
                }
            }
        }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Currency",
                style =
                    KurrentTypography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd),
            ) {
                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search currency...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search",
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(MaterialTheme.dimensions.cardRadius),
                )

                // Currency list with LazyColumn for performance
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false),
                ) {
                    items(
                        items = filteredCurrencies,
                        key = { it.currencyCode },
                    ) { currency ->
                        CurrencyItem(
                            currency = currency,
                            onClick = { onCurrencySelect(currency) },
                        )
                        if (currency != filteredCurrencies.last()) {
                            HorizontalDivider(
                                modifier =
                                    Modifier.padding(
                                        horizontal = MaterialTheme.dimensions.spacingMd,
                                    ),
                            )
                        }
                    }
                }

                if (filteredCurrencies.isEmpty()) {
                    Text(
                        text = "No currencies found",
                        style = KurrentTypography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.dimensions.spacingLg),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}

@Composable
private fun CurrencyItem(
    currency: Currency,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(
                    horizontal = MaterialTheme.dimensions.spacingMd,
                    vertical = MaterialTheme.dimensions.spacingSm,
                ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = currency.iconUrl,
            contentDescription = currency.currencyName,
            modifier = Modifier.size(40.dp),
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = currency.currencyCode,
                style =
                    KurrentTypography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = currency.currencyName,
                style = KurrentTypography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    KurrentTheme(darkTheme = true) {
        CalculatorScreenContent(
            uiState =
                CalculatorUiState(
                    isLoading = false,
                    currencies =
                        listOf(
                            Currency(
                                currencyCode = "SGD",
                                currencyName = "Singapore Dollar",
                                countryName = "Singapore",
                                countryCode = "SG",
                                rateToUsd = "1.35",
                                iconUrl = "https://flagcdn.com/w320/sg.png",
                                isAvailable = true,
                            ),
                            Currency(
                                currencyCode = "EUR",
                                currencyName = "Euro",
                                countryName = "European Union",
                                countryCode = "EU",
                                rateToUsd = "0.92",
                                iconUrl = "https://flagcdn.com/w320/eu.png",
                                isAvailable = true,
                            ),
                        ),
                    selectedCurrency =
                        Currency(
                            currencyCode = "SGD",
                            currencyName = "Singapore Dollar",
                            countryName = "Singapore",
                            countryCode = "SG",
                            rateToUsd = "1.35",
                            iconUrl = "https://flagcdn.com/w320/sg.png",
                            isAvailable = true,
                        ),
                    amount = "1000.00",
                    convertedAmount = "740.74",
                    targetCurrency = "USD",
                ),
            onNavigateBack = {},
            onCurrencySelect = {},
            onAmountChange = {},
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun CalculatorScreenLoadingPreview() {
    KurrentTheme(darkTheme = false) {
        CalculatorScreenContent(
            uiState = CalculatorUiState(isLoading = true),
            onNavigateBack = {},
            onCurrencySelect = {},
            onAmountChange = {},
        )
    }
}

@Preview(showBackground = true, name = "Amount Input Card")
@Composable
private fun AmountInputCardPreview() {
    KurrentTheme(darkTheme = true) {
        AmountInputCard(
            selectedCurrency =
                Currency(
                    currencyCode = "SGD",
                    currencyName = "Singapore Dollar",
                    countryName = "Singapore",
                    countryCode = "SG",
                    rateToUsd = "1.35",
                    iconUrl = "https://flagcdn.com/w320/sg.png",
                    isAvailable = true,
                ),
            currencies = emptyList(),
            amount = "1000.00",
            onCurrencySelect = {},
            onAmountChange = {},
        )
    }
}

@Preview(showBackground = true, name = "Conversion Result Card")
@Composable
private fun ConversionResultCardPreview() {
    KurrentTheme(darkTheme = true) {
        ConversionResultCard(
            targetCurrency = "USD",
            convertedAmount = "740.74",
        )
    }
}

@Preview(showBackground = true, name = "Exchange Rate Info")
@Composable
private fun ExchangeRateInfoPreview() {
    KurrentTheme(darkTheme = false) {
        ExchangeRateInfo(
            currency =
                Currency(
                    currencyCode = "EUR",
                    currencyName = "Euro",
                    countryName = "European Union",
                    countryCode = "EU",
                    rateToUsd = "0.92",
                    iconUrl = "https://flagcdn.com/w320/eu.png",
                    isAvailable = true,
                ),
            targetCurrency = "USD",
        )
    }
}
