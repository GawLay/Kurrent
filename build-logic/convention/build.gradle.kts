plugins {
    `kotlin-dsl`
}

group = "test.kyrie.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationConvention") {
            id = "kurrent.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeatureConvention") {
            id = "kurrent.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibraryConvention") {
            id = "kurrent.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("ktlintConvention") {
            id = "kurrent.ktlint"
            implementationClass = "KtlintConventionPlugin"
        }
    }
}

