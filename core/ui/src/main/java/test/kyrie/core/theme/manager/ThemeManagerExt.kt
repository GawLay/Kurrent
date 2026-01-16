package test.kyrie.core.theme.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ThemeManager.observeThemeMode(): ThemeMode {
    val themeMode by themeMode.collectAsState()
    return themeMode
}
