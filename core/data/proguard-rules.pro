#line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

-keep class test.kyrie.core.utility.CryptoHelperBridge { *; }
-keep class test.kyrie.core.utility.CryptoValue { *; }

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class test.kyrie.core.data.local.database.KurrentDatabase_Impl { *; }

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-keep @dagger.Module class test.kyrie.core.data.di.** { *; }
-keep @dagger.hilt.InstallIn class test.kyrie.core.data.di.** { *; }

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

#parameter names for better debugging
-keepparameternames