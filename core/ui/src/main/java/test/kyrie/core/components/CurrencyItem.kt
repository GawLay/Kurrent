package test.kyrie.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.core.theme.dimensions

@Composable
fun CurrencyItem(
    currencyCode: String,
    exchangeRate: String,
    iconUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
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
            AsyncImage(
                model = iconUrl,
                contentDescription = "$currencyCode flag",
                placeholder = rememberVectorPainter(Icons.Default.Flag),
                error = rememberVectorPainter(Icons.Default.ErrorOutline),
                modifier = Modifier
                    .size(MaterialTheme.dimensions.currencyFlagSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            // Currency code
            Text(
                text = currencyCode, style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ), color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Exchange rate
        Text(
            text = exchangeRate, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal
            ), color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


private const val PREVIEW_ICON_URL =
    "https://d-cb.jc-cdn.com/sites/crackberry.com/files/topic_images/2013/ANDROID.png"


@Preview(
    name = "Currency Item – Light", showBackground = false
)

@Composable
private fun CurrencyItemPreviewLight() {
    KurrentTheme(darkTheme = false) {
        KurrentCardView {
            CurrencyItem(
                currencyCode = "USD", exchangeRate = "148.50", iconUrl = PREVIEW_ICON_URL
            )
        }
    }
}

@Preview(
    name = "Currency Item – Dark", showBackground = false
)
@Composable
private fun CurrencyItemPreviewDark() {
    KurrentTheme(darkTheme = true) {
        KurrentCardView {
            CurrencyItem(
                currencyCode = "JPY", exchangeRate = "0.67", iconUrl = PREVIEW_ICON_URL
            )
        }
    }
}
