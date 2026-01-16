plugins {
    id("kurrent.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "test.kyrie.core.domain"
}

dependencies {
    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}
