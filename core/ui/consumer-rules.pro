# Compose UI - Keep runtime and reflection-based components
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material3.** { *; }
-dontwarn androidx.compose.material3.**

# Keep all @Composable functions
-keepclasseswithmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Compose animation
-keep class androidx.compose.animation.** { *; }

# Theme and styling
-keep class androidx.compose.ui.graphics.** { *; }
-keep class androidx.compose.ui.unit.** { *; }

# Hilt integration (if theme components use Hilt)
-keep interface * extends dagger.hilt.EntryPoint { *; }

# Navigation Compose
-keep class androidx.navigation.compose.** { *; }

# Lifecycle integration
-keep class androidx.lifecycle.** { *; }
-keep class androidx.lifecycle.viewmodel.compose.** { *; }
