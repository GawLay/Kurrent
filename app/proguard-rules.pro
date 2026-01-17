# Keep application class
-keep class test.kyrie.kurrent.KurrentApp { *; }

# Keep Hilt generated components
-keep class dagger.hilt.android.internal.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep MainActivity and activities
-keep class test.kyrie.kurrent.MainActivity { *; }
-keep public class * extends android.app.Activity
-keep public class * extends androidx.appcompat.app.AppCompatActivity

# Navigation component
-keep class * extends androidx.navigation.Navigator

# Compose Navigation
-keep class androidx.navigation.compose.** { *; }

# Keep R8 optimization attributes
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
}

# Crashlytics (if you add it later)
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception