plugins {
    id("kurrent.android.feature")
}

android {
    namespace = "test.kyrie.core.ui"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.ui.tooling)
}