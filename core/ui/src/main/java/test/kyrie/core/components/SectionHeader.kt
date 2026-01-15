package test.kyrie.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import test.kyrie.core.theme.KurrentTextStyles
import test.kyrie.core.theme.dimensions

/**
 * Section header for currency list
 * Displays title and optional subtitle
 */
@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimensions.paddingMd,
                vertical = MaterialTheme.dimensions.paddingSm
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        subtitle?.let {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.textGapXs))
            Text(
                text = it,
                style = KurrentTextStyles.supportingText,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
