plugins {
    id("kurrent.android.feature")
//    alias(libs.plugins.ksp)
}

android {
    namespace = "test.kyrie.core.utility"
}

dependencies {
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    debugImplementation(libs.ui.tooling)
}