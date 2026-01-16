package test.kyrie.kurrent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.core.theme.manager.ThemeManager
import test.kyrie.core.theme.manager.ThemeManagerEntryPoint
import test.kyrie.core.theme.manager.ThemeMode
import test.kyrie.core.theme.manager.observeThemeMode
import test.kyrie.kurrent.navigation.KurrentNavGraph
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeMode = themeManager.observeThemeMode()
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            KurrentTheme(darkTheme = darkTheme) {
                KurrentNavGraph()
            }
        }
    }
}