plugins {
    id("kurrent.android.application")
}

android {
    namespace = "test.kyrie.kurrent"
}


dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":feature:currency-list"))
    implementation(project(":feature:calculator"))
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.compose.test)
    debugImplementation(libs.bundles.compose.debug)
}