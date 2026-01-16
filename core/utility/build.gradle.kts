plugins {
    id("kurrent.android.feature")
//    alias(libs.plugins.ksp)
}

android {
    namespace = "test.kyrie.core.utility"

    defaultConfig {
//        externalNativeBuild {
//            cmake {
//                cppFlags += ""
//            }
//        }
        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    debugImplementation(libs.ui.tooling)
}