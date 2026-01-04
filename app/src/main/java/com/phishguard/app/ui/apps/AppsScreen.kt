package com.phishguard.app.ui.apps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppsScreen() {

    var apps by remember {
        mutableStateOf(
            listOf(
                ProtectedApp("Instagram", true),
                ProtectedApp("YouTube", false),
                ProtectedApp("Facebook", false)
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(apps) { app ->
            AppToggleItem(
                appName = app.name,
                enabled = app.enabled
            ) { isEnabled ->
                apps = apps.map {
                    if (it.name == app.name)
                        it.copy(enabled = isEnabled)
                    else it
                }
            }
        }
    }
}
