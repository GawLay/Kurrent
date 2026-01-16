plugins {
    id("kurrent.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}


android {
    namespace = "test.kyrie.core.data"
}


dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:utility"))

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.network)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.bundles.coroutines)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}