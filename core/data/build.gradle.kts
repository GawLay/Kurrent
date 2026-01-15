plugins {
    id("kurrent.android.library")
    alias(libs.plugins.ksp)
}

android {
    namespace = "test.kyrie.core.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)


    implementation(libs.bundles.coroutines)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}