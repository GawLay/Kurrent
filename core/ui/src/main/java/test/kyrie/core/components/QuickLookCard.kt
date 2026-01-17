package test.kyrie.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import test.kyrie.core.theme.KurrentTextStyles
import test.kyrie.core.theme.dimensions

/**
 * Quick look card showing saved currency conversion
 * Displays at the top of the currency list screen
 */
@Composable
fun QuickLookCard(
    fromAmount: String,
    fromCurrency: String,
    toAmount: String,
    toCurrency: String,
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
            // Title
            Text(
                text = "Quick Look",
                style =
                    KurrentTextStyles.sectionTitle.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )

            // Conversion display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // From currency
                Text(
                    text = "$fromAmount $fromCurrency",
                    style =
                        KurrentTextStyles.currencyAmount.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                // Arrow icon
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "converts to",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                )

                // To currency
                Text(
                    text = "$toAmount $toCurrency",
                    style =
                        KurrentTextStyles.currencyAmount.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            // Label
            Text(
                text = "Saved Currency",
                style = KurrentTextStyles.supportingText,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
            )
        }
    }
}
