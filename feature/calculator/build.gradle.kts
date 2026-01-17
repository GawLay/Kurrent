plugins {
    id("kurrent.android.feature")
}

android {
    namespace = "test.kyrie.feature.calculator"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:utility"))
    implementation(project(":core:data"))

//    implementation(libs.bundles.hilt)
//    ksp(libs.hilt.compiler)
//    implementation(libs.bundles.coroutines)

//    implementation(libs.bundles.android.ui)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.bundles.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}
