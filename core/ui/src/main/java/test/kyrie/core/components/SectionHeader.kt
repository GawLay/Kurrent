package test.kyrie.core.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import test.kyrie.core.theme.dimensions


@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    currencyBaseTitle: String,
    availableCurrencies: Map<String, String> = emptyMap(),
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimensions.paddingMd,
                vertical = MaterialTheme.dimensions.paddingSm
            ),
        verticalAlignment = Alignment.CenterVertically


    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onBackground
        )


        Spacer(modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.spacingSm))


        KurrentCardView(
            modifier = Modifier
                .weight(1f),
            containerColor =  MaterialTheme.colorScheme.secondary
        ) {
            Text(
                text = currencyBaseTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }


    }
}