package com.project.mobile.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val datastore: DataStore<Preferences>
) {

    suspend fun getTheme(): Result<Int> {
        return Result.runCatching {
            val value = datastore.data.map { preferences -> preferences[KEY_THEME] ?: 1 }.first()
            value
        }
    }

    suspend fun setTheme(theme: Int) {
        Result.runCatching {
            datastore.edit { preferences ->
                preferences[KEY_THEME] = theme
            }
        }
    }

    private companion object {
        val KEY_THEME = intPreferencesKey("theme")
    }
}