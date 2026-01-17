package test.kyrie.core.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import test.kyrie.core.theme.dimensions

@Composable
fun KurrentCardView(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(MaterialTheme.dimensions.cardRadius),
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    elevation: Dp = MaterialTheme.dimensions.elevationSm,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors =
            CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = elevation,
            ),
        content = content,
    )
}
