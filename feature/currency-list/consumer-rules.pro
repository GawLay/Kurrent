# Hilt
-dontwarn com.google.errorprone.annotations.**
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep Hilt entry points (important for ThemeManagerEntryPoint)
-keep interface * extends dagger.hilt.EntryPoint { *; }

# Compose
-dontwarn androidx.compose.material3.**
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }

# Navigation
-keep class androidx.navigation.** { *; }
-keepclassmembers class * extends androidx.navigation.Navigator {
    <init>(...);
}
