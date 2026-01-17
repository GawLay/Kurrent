package test.kyrie.core.theme.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager
    @Inject
    constructor(
        private val themePreferences: ThemePreferences,
    ) {
        private val _themeMode = MutableStateFlow(themePreferences.getThemeMode())
        val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

        fun toggleTheme() {
            val newMode =
                when (_themeMode.value) {
                    ThemeMode.LIGHT -> ThemeMode.DARK
                    ThemeMode.DARK -> ThemeMode.LIGHT
                    ThemeMode.SYSTEM -> ThemeMode.DARK // If system, switch to dark
                }
            setThemeMode(newMode)
        }

        fun setThemeMode(mode: ThemeMode) {
            _themeMode.value = mode
            themePreferences.setThemeMode(mode)
        }

        fun getCurrentThemeMode(): ThemeMode = _themeMode.value
    }
