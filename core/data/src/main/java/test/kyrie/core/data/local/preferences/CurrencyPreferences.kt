package test.kyrie.core.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    var lastFetchTimestamp: Long
        get() = prefs.getLong(KEY_LAST_FETCH_TIMESTAMP, 0L)
        set(value) = prefs.edit().putLong(KEY_LAST_FETCH_TIMESTAMP, value).apply()

    companion object {
        private const val PREFS_NAME = "currency_preferences"
        private const val KEY_LAST_FETCH_TIMESTAMP = "last_fetch_timestamp"
    }
}

