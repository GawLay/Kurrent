package test.kyrie.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
@ReadOnlyComposable
fun cardGradientBrush(darkTheme: Boolean): Brush {
    return if (darkTheme) {
        Brush.verticalGradient(
            colors = listOf(
                KurrentColors.cardGradientStart(darkTheme),
                KurrentColors.cardGradientEnd(darkTheme)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                KurrentColors.cardGradientStart(darkTheme),
                KurrentColors.cardGradientEnd(darkTheme)
            )
        )
    }
}

@Composable
@ReadOnlyComposable
fun getCardElevation(darkTheme: Boolean): androidx.compose.ui.unit.Dp {
    val dims = MaterialTheme.dimensions
    return if (darkTheme) dims.elevationMd else dims.elevationSm
}

data class CurrencyItemColors(
    val background: Color,
    val contentColor: Color,
    val codeColor: Color,
    val nameColor: Color,
    val amountColor: Color
)

@Composable
@ReadOnlyComposable
fun getCurrencyItemColors(
    selected: Boolean = false,
    darkTheme: Boolean
): CurrencyItemColors {
    return if (selected) {
        CurrencyItemColors(
            background = KurrentColors.currencyHighlight(darkTheme),
            contentColor = KurrentColors.textPrimary(darkTheme),
            codeColor = KurrentColors.amountEmphasis(darkTheme),
            nameColor = KurrentColors.textPrimary(darkTheme),
            amountColor = KurrentColors.amountEmphasis(darkTheme)
        )
    } else {
        CurrencyItemColors(
            background = KurrentColors.cardBackground(darkTheme),
            contentColor = KurrentColors.textPrimary(darkTheme),
            codeColor = KurrentColors.textPrimary(darkTheme),
            nameColor = KurrentColors.textSecondary(darkTheme),
            amountColor = KurrentColors.textPrimary(darkTheme)
        )
    }
}

/**
 * Quick access to common dimension values
 */
object ThemeValues {
    @Composable
    @ReadOnlyComposable
    fun cardRadius() = MaterialTheme.dimensions.cardRadius
    
    @Composable
    @ReadOnlyComposable
    fun screenPadding() = MaterialTheme.dimensions.screenPaddingHorizontal
    
    @Composable
    @ReadOnlyComposable
    fun cardPadding() = MaterialTheme.dimensions.cardPadding
    
    @Composable
    @ReadOnlyComposable
    fun spacingMedium() = MaterialTheme.dimensions.spacingMd
    
    @Composable
    @ReadOnlyComposable
    fun spacingLarge() = MaterialTheme.dimensions.spacingLg
}
