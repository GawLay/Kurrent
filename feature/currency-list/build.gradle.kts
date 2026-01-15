plugins {
    id("kurrent.android.feature")
}

android {
    namespace = "test.kyrie.feature.currency_list"
}

dependencies {
    implementation(project(":core:ui"))
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.ui.tooling)
}