plugins {
    id("kurrent.android.feature")
}

android {
    namespace = "test.kyrie.feature.calculator"
}

dependencies {
    // Use bundles - much cleaner!
    implementation(libs.bundles.android.ui)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}
