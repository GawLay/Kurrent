plugins {
    id("kurrent.android.feature")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "test.kyrie.feature.currencyList"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:utility"))
    implementation(project(":core:data"))

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.bundles.coroutines)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.ui.tooling)
}
