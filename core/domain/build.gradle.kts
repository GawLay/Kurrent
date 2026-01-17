plugins {
    id("kurrent.android.library")
}

android {
    namespace = "test.kyrie.core.domain"
}

dependencies {
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}
