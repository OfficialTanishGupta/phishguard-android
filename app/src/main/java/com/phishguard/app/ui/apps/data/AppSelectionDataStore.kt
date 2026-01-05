package com.phishguard.app.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "app_selection")

class AppSelectionDataStore(private val context: Context) {

    private val SELECTED_APPS_KEY =
        stringSetPreferencesKey("selected_apps")

    val selectedApps: Flow<Set<String>> =
        context.dataStore.data.map { prefs ->
            prefs[SELECTED_APPS_KEY] ?: emptySet()
        }

    suspend fun saveApp(packageName: String, enabled: Boolean) {
        context.dataStore.edit { prefs ->
            val current = prefs[SELECTED_APPS_KEY] ?: emptySet()
            prefs[SELECTED_APPS_KEY] =
                if (enabled) current + packageName
                else current - packageName
        }
    }
}
