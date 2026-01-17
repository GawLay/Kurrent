package test.kyrie.core.theme.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) {
        private val prefs: SharedPreferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE,
            )

        fun getThemeMode(): ThemeMode {
            val mode = prefs.getString(KEY_THEME_MODE, ThemeMode.DARK.name)
            return try {
                ThemeMode.valueOf(mode ?: ThemeMode.DARK.name)
            } catch (e: IllegalArgumentException) {
                ThemeMode.DARK
            }
        }

        @SuppressLint("UseKtx")
        fun setThemeMode(themeMode: ThemeMode) {
            prefs.edit().putString(KEY_THEME_MODE, themeMode.name).apply()
        }

        companion object {
            private const val PREFS_NAME = "kurrent_theme_preferences"
            private const val KEY_THEME_MODE = "theme_mode"
        }
    }

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}
