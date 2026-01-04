package com.phishguard.app.ui.apps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppToggleItem(
    appName: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = appName,
                style = MaterialTheme.typography.titleMedium
            )
            Switch(
                checked = enabled,
                onCheckedChange = onToggle
            )
        }
    }
}
