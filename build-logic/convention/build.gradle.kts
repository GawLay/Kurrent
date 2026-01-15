plugins {
    `kotlin-dsl`
}

group = "test.kyrie.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidFeatureConvention") {
            id = "kurrent.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibraryConvention") {
            id = "kurrent.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}

