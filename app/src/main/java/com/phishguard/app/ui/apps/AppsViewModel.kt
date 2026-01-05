package com.phishguard.app.ui.apps

import android.app.Application
import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import com.phishguard.app.data.AppSelectionDataStore
import kotlinx.coroutines.flow.combine

class AppsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore =
        AppSelectionDataStore(application.applicationContext)

    private val _apps = MutableStateFlow<List<InstalledApp>>(emptyList())
    val apps: StateFlow<List<InstalledApp>> = _apps

    init {
        loadAppsWithSelection()
    }

    private fun loadAppsWithSelection() {
        viewModelScope.launch {
            val pm = getApplication<Application>().packageManager

            val intent = android.content.Intent(android.content.Intent.ACTION_MAIN).apply {
                addCategory(android.content.Intent.CATEGORY_LAUNCHER)
            }

            val launcherApps = pm.queryIntentActivities(intent, 0)
                .map {
                    InstalledApp(
                        name = it.loadLabel(pm).toString(),
                        packageName = it.activityInfo.packageName,
                        icon = it.loadIcon(pm)
                    )
                }
                .distinctBy { it.packageName }

            dataStore.selectedApps.collect { selected ->
                _apps.value = launcherApps
                    .sortedBy { it.name.lowercase() }
                    .map {
                        it.copy(enabled = selected.contains(it.packageName))
                    }
            }
        }
    }

    fun toggleApp(packageName: String, enabled: Boolean) {
        viewModelScope.launch {
            dataStore.saveApp(packageName, enabled)
        }
    }
}
