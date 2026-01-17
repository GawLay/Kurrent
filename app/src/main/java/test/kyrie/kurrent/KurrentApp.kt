package test.kyrie.kurrent

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KurrentApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
