plugins {
    id("kurrent.android.feature")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}


android {
    namespace = "test.kyrie.feature.currency_list"
}


dependencies {
    implementation(project(":core:ui"))
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.coroutines)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.ui.tooling)
}