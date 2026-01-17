package test.kyrie.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimensions(
    // Spacing
    val spacingXs: Dp,
    val spacingSm: Dp,
    val spacingMd: Dp,
    val spacingLg: Dp,
    val spacingXl: Dp,
    val spacingXxl: Dp,
    // Padding
    val paddingXs: Dp,
    val paddingSm: Dp,
    val paddingMd: Dp,
    val paddingLg: Dp,
    val paddingXl: Dp,
    // Screen padding
    val screenPaddingHorizontal: Dp,
    val screenPaddingVertical: Dp,
    val screenPaddingTop: Dp,
    val screenPaddingBottom: Dp,
    // Card
    val cardRadius: Dp,
    val cardRadiusSmall: Dp,
    val cardRadiusLarge: Dp,
    val cardPadding: Dp,
    val cardPaddingHorizontal: Dp,
    val cardPaddingVertical: Dp,
    val cardSpacing: Dp,
    // Elevation
    val elevationNone: Dp,
    val elevationXs: Dp,
    val elevationSm: Dp,
    val elevationMd: Dp,
    val elevationLg: Dp,
    // Text gaps
    val textGapXs: Dp,
    val textGapSm: Dp,
    val textGapMd: Dp,
    val textGapLg: Dp,
    // Icons
    val iconSizeSmall: Dp,
    val iconSizeMedium: Dp,
    val iconSizeLarge: Dp,
    // Currency
    val currencyItemHeight: Dp,
    val currencyFlagSize: Dp,
    val currencyCodeMinWidth: Dp,
    // Calculator
    val calculatorButtonSize: Dp,
    val calculatorButtonRadius: Dp,
    val calculatorInputHeight: Dp,
)

val DefaultDimensions =
    Dimensions(
        spacingXs = 4.dp,
        spacingSm = 8.dp,
        spacingMd = 16.dp,
        spacingLg = 24.dp,
        spacingXl = 32.dp,
        spacingXxl = 48.dp,
        paddingXs = 4.dp,
        paddingSm = 8.dp,
        paddingMd = 16.dp,
        paddingLg = 24.dp,
        paddingXl = 32.dp,
        screenPaddingHorizontal = 16.dp,
        screenPaddingVertical = 16.dp,
        screenPaddingTop = 16.dp,
        screenPaddingBottom = 16.dp,
        cardRadius = 16.dp,
        cardRadiusSmall = 8.dp,
        cardRadiusLarge = 24.dp,
        cardPadding = 16.dp,
        cardPaddingHorizontal = 20.dp,
        cardPaddingVertical = 16.dp,
        cardSpacing = 12.dp,
        elevationNone = 0.dp,
        elevationXs = 1.dp,
        elevationSm = 2.dp,
        elevationMd = 4.dp,
        elevationLg = 8.dp,
        textGapXs = 2.dp,
        textGapSm = 4.dp,
        textGapMd = 8.dp,
        textGapLg = 12.dp,
        iconSizeSmall = 16.dp,
        iconSizeMedium = 24.dp,
        iconSizeLarge = 32.dp,
        currencyItemHeight = 60.dp,
        currencyFlagSize = 32.dp,
        currencyCodeMinWidth = 48.dp,
        calculatorButtonSize = 64.dp,
        calculatorButtonRadius = 32.dp,
        calculatorInputHeight = 80.dp,
    )

val CompactDimensions =
    DefaultDimensions.copy(
        spacingXs = 2.dp,
        spacingSm = 6.dp,
        spacingMd = 12.dp,
        spacingLg = 18.dp,
        spacingXl = 24.dp,
        spacingXxl = 36.dp,
        iconSizeSmall = 14.dp,
        iconSizeMedium = 20.dp,
        iconSizeLarge = 28.dp,
        currencyItemHeight = 52.dp,
        currencyFlagSize = 28.dp,
    )
val LocalDimensions = staticCompositionLocalOf { DefaultDimensions }
