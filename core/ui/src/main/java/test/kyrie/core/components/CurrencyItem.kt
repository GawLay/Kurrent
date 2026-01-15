package test.kyrie.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.core.theme.dimensions

@Composable
fun CurrencyItem(
    currencyCode: String,
    exchangeRate: String,
    flagEmoji: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = MaterialTheme.dimensions.paddingMd,
                vertical = MaterialTheme.dimensions.paddingMd
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.spacingMd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Currency flag
            Box(
                modifier = Modifier
                    .size(MaterialTheme.dimensions.currencyFlagSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = flagEmoji,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Currency code
            Text(
                text = currencyCode,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Exchange rate
        Text(
            text = exchangeRate,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Preview(
    name = "Currency Item – Light",
    showBackground = false
)
@Composable
private fun CurrencyItemPreviewLight() {
    KurrentTheme(darkTheme = false) {
        KurrentCardView {
            CurrencyItem(
                currencyCode = "USD",
                exchangeRate = "148.50",
                flagEmoji = "🇺🇸"
            )
        }
    }
}

@Preview(
    name = "Currency Item – Dark",
    showBackground = false
)
@Composable
private fun CurrencyItemPreviewDark() {
    KurrentTheme(darkTheme = true) {
        KurrentCardView {
            CurrencyItem(
                currencyCode = "JPY",
                exchangeRate = "0.67",
                flagEmoji = "🇯🇵"
            )
        }
    }
}
