plugins {
    id("kurrent.android.library")
}


android {
    namespace = "test.kyrie.core.data"
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}


dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:utility"))

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.network)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}