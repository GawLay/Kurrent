# DTO models
-keep class test.kyrie.core.data.model.** { *; }
-keepclassmembers class test.kyrie.core.data.model.** { *; }

# Gson annotations
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod

# Room database and entities
-keep class test.kyrie.core.data.local.database.** { *; }
-keep class test.kyrie.core.data.local.entity.** { *; }
-keep class test.kyrie.core.data.local.dao.** { *; }

# Room annotations
-keep @androidx.room.Entity class *
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Dao class * { *; }

# Room generated code
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.ColumnInfo class * { *; }
-dontwarn androidx.room.paging.**


-keep class test.kyrie.core.data.repository.** { *; }
-keep interface test.kyrie.core.data.repository.** { *; }

-keep class test.kyrie.core.data.remote.** { *; }
-keep interface test.kyrie.core.data.remote.** { *; }

-keep class test.kyrie.core.data.remote.interceptor.** { *; }

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

# Retrofit annotations
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Call Signature, Response (R8 full mode strips signatures from non-kept items)
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# OkHttp
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**


# Gson TypeToken
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

-keepattributes Signature

# Gson specific classes
-dontwarn sun.misc.**

#model class fields
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

-keepclassmembers class * {
    @javax.inject.Inject <fields>;
}

-keepclasseswithmembernames class * {
    @javax.inject.Inject <init>(...);
}

-keep class test.kyrie.core.data.mapper.** { *; }

-keepattributes *Annotation*
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.**

# Keep Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# line numbers for debugging
-keepattributes SourceFile,LineNumberTable

# custom exceptions
-keep public class * extends java.lang.Exception

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}