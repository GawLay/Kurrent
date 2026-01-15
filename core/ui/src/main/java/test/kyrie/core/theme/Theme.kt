package test.kyrie.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark Color Scheme for Kurrent
 * Features: True black background, cool blue tones, high contrast text
 */
private val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary = DarkColors.Primary,
    onPrimary = DarkColors.OnPrimary,
    primaryContainer = DarkColors.PrimaryVariant,
    onPrimaryContainer = DarkColors.TextPrimary,
    
    // Secondary colors
    secondary = DarkColors.Secondary,
    onSecondary = DarkColors.OnPrimary,
    secondaryContainer = DarkColors.CardBackgroundElevated,
    onSecondaryContainer = DarkColors.TextPrimary,
    
    // Background and Surface
    background = DarkColors.Background,
    onBackground = DarkColors.OnBackground,
    surface = DarkColors.Surface,
    onSurface = DarkColors.OnSurface,
    surfaceVariant = DarkColors.SurfaceVariant,
    onSurfaceVariant = DarkColors.TextSecondary,
    
    // Surface containers (for cards)
    surfaceContainer = DarkColors.CardBackground,
    surfaceContainerHigh = DarkColors.CardBackgroundElevated,
    surfaceContainerHighest = DarkColors.SurfaceVariant,
    
    // Tertiary colors (for accents)
    tertiary = DarkColors.CurrencyHighlight,
    onTertiary = DarkColors.OnPrimary,
    tertiaryContainer = DarkColors.CardBackground,
    onTertiaryContainer = DarkColors.TextPrimary,
    
    // Error colors
    error = DarkColors.Error,
    onError = DarkColors.OnPrimary,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = DarkColors.TextPrimary,
    
    // Outline
    outline = DarkColors.Border,
    outlineVariant = DarkColors.Divider,
    
    // Scrim
    scrim = Color(0xFF000000)
)

private val LightColorScheme = lightColorScheme(
    // Primary colors
    primary = LightColors.Primary,
    onPrimary = LightColors.OnPrimary,
    primaryContainer = Color(0xFFD6E7FF),
    onPrimaryContainer = Color(0xFF001C3B),
    
    // Secondary colors
    secondary = LightColors.Secondary,
    onSecondary = LightColors.OnPrimary,
    secondaryContainer = LightColors.CardBackgroundElevated,
    onSecondaryContainer = LightColors.TextPrimary,
    
    // Background and Surface
    background = LightColors.Background,
    onBackground = LightColors.OnBackground,
    surface = LightColors.Surface,
    onSurface = LightColors.OnSurface,
    surfaceVariant = LightColors.SurfaceVariant,
    onSurfaceVariant = LightColors.TextSecondary,
    
    // Surface containers (for cards)
    surfaceContainer = LightColors.CardBackground,
    surfaceContainerHigh = LightColors.CardBackgroundElevated,
    surfaceContainerHighest = LightColors.SurfaceVariant,
    
    // Tertiary colors (for accents)
    tertiary = LightColors.CurrencyHighlight,
    onTertiary = LightColors.OnPrimary,
    tertiaryContainer = Color(0xFFD6E7FF),
    onTertiaryContainer = LightColors.TextPrimary,
    
    // Error colors
    error = LightColors.Error,
    onError = LightColors.OnPrimary,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    
    // Outline
    outline = LightColors.Border,
    outlineVariant = LightColors.Divider,
    
    // Scrim
    scrim = Color(0xFF000000)
)

/**
 * Main Kurrent Theme Composable
 * 
 * @param darkTheme Whether to use dark theme (default: system preference)
 * @param dynamicColor Whether to use dynamic color (Android 12+, default: false to ensure custom colors)
 * @param dimensions Which dimension set to use (default: Default, can use Compact for smaller screens)
 * @param content The composable content
 */
@Composable
fun KurrentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled by default to use custom colors
    dimensions: Dimensions = Dimensions.Default,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalDimensions provides dimensions as Dimensions.Default
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = KurrentTypography,
            content = content
        )
    }
}

/**
 * Extension property to access dimensions from MaterialTheme
 * Usage: MaterialTheme.dimensions.spacingMd
 */
val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current

/**
 * Extension properties to access custom color tokens
 */
object KurrentColors {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme
    
    @Composable
    @ReadOnlyComposable
    fun cardBackground(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.CardBackground else LightColors.CardBackground
    }
    
    @Composable
    @ReadOnlyComposable
    fun cardBackgroundElevated(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.CardBackgroundElevated else LightColors.CardBackgroundElevated
    }
    
    @Composable
    @ReadOnlyComposable
    fun cardGradientStart(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.CardGradientStart else LightColors.CardGradientStart
    }
    
    @Composable
    @ReadOnlyComposable
    fun cardGradientEnd(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.CardGradientEnd else LightColors.CardGradientEnd
    }
    
    @Composable
    @ReadOnlyComposable
    fun textPrimary(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.TextPrimary else LightColors.TextPrimary
    }
    
    @Composable
    @ReadOnlyComposable
    fun textSecondary(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.TextSecondary else LightColors.TextSecondary
    }
    
    @Composable
    @ReadOnlyComposable
    fun textTertiary(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.TextTertiary else LightColors.TextTertiary
    }
    
    @Composable
    @ReadOnlyComposable
    fun currencyHighlight(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.CurrencyHighlight else LightColors.CurrencyHighlight
    }
    
    @Composable
    @ReadOnlyComposable
    fun amountEmphasis(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.AmountEmphasis else LightColors.AmountEmphasis
    }
    
    @Composable
    @ReadOnlyComposable
    fun divider(darkTheme: Boolean = isSystemInDarkTheme()): Color {
        return if (darkTheme) DarkColors.Divider else LightColors.Divider
    }
}