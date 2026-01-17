# DTO models
-keep class test.kyrie.core.data.model.** { *; }

# Room database and entities
-keep class test.kyrie.core.data.local.database.** { *; }
-keep class test.kyrie.core.data.local.entity.** { *; }
-keep class test.kyrie.core.data.local.dao.** { *; }

# Room annotations
-keep @androidx.room.Entity class *
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Repository implementations
-keep class test.kyrie.core.data.repository.** { *; }

# Remote API
-keep class test.kyrie.core.data.remote.** { *; }
-keep class test.kyrie.core.data.remote.interceptor.** { *; }

# Retrofit
-keepattributes Signature, *Annotation*, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# OkHttp
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# Gson
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
-dontwarn sun.misc.**
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keepclassmembers class * {
    @javax.inject.Inject <fields>;
}
-keepclasseswithmembernames class * {
    @javax.inject.Inject <init>(...);
}

# Mappers
-keep class test.kyrie.core.data.mapper.** { *; }

# Kotlin & Coroutines
-keep class kotlin.Metadata { *; }
-keepattributes *Annotation*
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-dontwarn kotlinx.coroutines.**

# Debugging
-keepattributes SourceFile,LineNumberTable

# Exceptions
-keep public class * extends java.lang.Exception

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}