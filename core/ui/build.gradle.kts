plugins {
    id("kurrent.android.feature")
    alias(libs.plugins.ksp)
}

android {
    namespace = "test.kyrie.core.ui"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.ui.tooling)
}