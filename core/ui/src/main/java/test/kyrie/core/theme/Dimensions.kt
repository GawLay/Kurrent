package test.kyrie.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Sealed class-based dimension system for Kurrent app.
 * Provides consistent spacing, padding, radius, elevation, and text gaps.
 * Access via MaterialTheme.dimensions extension.
 */
sealed class Dimensions {
    
    // Spacing values
    abstract val spacingXs: Dp
    abstract val spacingSm: Dp
    abstract val spacingMd: Dp
    abstract val spacingLg: Dp
    abstract val spacingXl: Dp
    abstract val spacingXxl: Dp
    
    // Padding values
    abstract val paddingXs: Dp
    abstract val paddingSm: Dp
    abstract val paddingMd: Dp
    abstract val paddingLg: Dp
    abstract val paddingXl: Dp
    
    // Screen padding
    abstract val screenPaddingHorizontal: Dp
    abstract val screenPaddingVertical: Dp
    abstract val screenPaddingTop: Dp
    abstract val screenPaddingBottom: Dp
    
    // Card dimensions
    abstract val cardRadius: Dp
    abstract val cardRadiusSmall: Dp
    abstract val cardRadiusLarge: Dp
    abstract val cardPadding: Dp
    abstract val cardPaddingHorizontal: Dp
    abstract val cardPaddingVertical: Dp
    abstract val cardSpacing: Dp
    
    // Elevation
    abstract val elevationNone: Dp
    abstract val elevationXs: Dp
    abstract val elevationSm: Dp
    abstract val elevationMd: Dp
    abstract val elevationLg: Dp
    
    // Text gaps/spacing
    abstract val textGapXs: Dp
    abstract val textGapSm: Dp
    abstract val textGapMd: Dp
    abstract val textGapLg: Dp
    
    // Icon sizes
    abstract val iconSizeSmall: Dp
    abstract val iconSizeMedium: Dp
    abstract val iconSizeLarge: Dp
    
    // Currency specific
    abstract val currencyItemHeight: Dp
    abstract val currencyFlagSize: Dp
    abstract val currencyCodeMinWidth: Dp
    
    // Calculator specific
    abstract val calculatorButtonSize: Dp
    abstract val calculatorButtonRadius: Dp
    abstract val calculatorInputHeight: Dp
    
    // Default implementation
    object Default : Dimensions() {
        // Spacing
        override val spacingXs = 4.dp
        override val spacingSm = 8.dp
        override val spacingMd = 16.dp
        override val spacingLg = 24.dp
        override val spacingXl = 32.dp
        override val spacingXxl = 48.dp
        
        // Padding
        override val paddingXs = 4.dp
        override val paddingSm = 8.dp
        override val paddingMd = 16.dp
        override val paddingLg = 24.dp
        override val paddingXl = 32.dp
        
        // Screen padding
        override val screenPaddingHorizontal = 16.dp
        override val screenPaddingVertical = 16.dp
        override val screenPaddingTop = 16.dp
        override val screenPaddingBottom = 16.dp
        
        // Card dimensions
        override val cardRadius = 16.dp
        override val cardRadiusSmall = 8.dp
        override val cardRadiusLarge = 24.dp
        override val cardPadding = 16.dp
        override val cardPaddingHorizontal = 20.dp
        override val cardPaddingVertical = 16.dp
        override val cardSpacing = 12.dp
        
        // Elevation
        override val elevationNone = 0.dp
        override val elevationXs = 1.dp
        override val elevationSm = 2.dp
        override val elevationMd = 4.dp
        override val elevationLg = 8.dp
        
        // Text gaps
        override val textGapXs = 2.dp
        override val textGapSm = 4.dp
        override val textGapMd = 8.dp
        override val textGapLg = 12.dp
        
        // Icon sizes
        override val iconSizeSmall = 16.dp
        override val iconSizeMedium = 24.dp
        override val iconSizeLarge = 32.dp
        
        // Currency specific
        override val currencyItemHeight = 60.dp
        override val currencyFlagSize = 32.dp
        override val currencyCodeMinWidth = 48.dp
        
        // Calculator specific
        override val calculatorButtonSize = 64.dp
        override val calculatorButtonRadius = 32.dp
        override val calculatorInputHeight = 80.dp
    }
    
    // Compact dimensions for smaller screens
    object Compact : Dimensions() {
        // Spacing
        override val spacingXs = 2.dp
        override val spacingSm = 6.dp
        override val spacingMd = 12.dp
        override val spacingLg = 18.dp
        override val spacingXl = 24.dp
        override val spacingXxl = 36.dp
        
        // Padding
        override val paddingXs = 2.dp
        override val paddingSm = 6.dp
        override val paddingMd = 12.dp
        override val paddingLg = 18.dp
        override val paddingXl = 24.dp
        
        // Screen padding
        override val screenPaddingHorizontal = 12.dp
        override val screenPaddingVertical = 12.dp
        override val screenPaddingTop = 12.dp
        override val screenPaddingBottom = 12.dp
        
        // Card dimensions
        override val cardRadius = 12.dp
        override val cardRadiusSmall = 6.dp
        override val cardRadiusLarge = 18.dp
        override val cardPadding = 12.dp
        override val cardPaddingHorizontal = 16.dp
        override val cardPaddingVertical = 12.dp
        override val cardSpacing = 8.dp
        
        // Elevation
        override val elevationNone = 0.dp
        override val elevationXs = 1.dp
        override val elevationSm = 2.dp
        override val elevationMd = 3.dp
        override val elevationLg = 6.dp
        
        // Text gaps
        override val textGapXs = 2.dp
        override val textGapSm = 3.dp
        override val textGapMd = 6.dp
        override val textGapLg = 9.dp
        
        // Icon sizes
        override val iconSizeSmall = 14.dp
        override val iconSizeMedium = 20.dp
        override val iconSizeLarge = 28.dp
        
        // Currency specific
        override val currencyItemHeight = 52.dp
        override val currencyFlagSize = 28.dp
        override val currencyCodeMinWidth = 44.dp
        
        // Calculator specific
        override val calculatorButtonSize = 56.dp
        override val calculatorButtonRadius = 28.dp
        override val calculatorInputHeight = 68.dp
    }
}

val LocalDimensions = staticCompositionLocalOf { Dimensions.Default }
