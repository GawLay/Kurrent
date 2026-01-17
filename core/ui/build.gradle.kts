plugins {
    id("kurrent.android.feature")
}

android {
    namespace = "test.kyrie.core.ui"
}

dependencies {
    debugImplementation(libs.ui.tooling)
}
