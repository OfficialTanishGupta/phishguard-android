package com.phishguard.app.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var isActive by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PhishGuard") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isActive)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Privacy Shield", style = MaterialTheme.typography.titleMedium)
                    Text(
                        if (isActive) "ACTIVE" else "PAUSED",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Masking Level")
                    Text("Medium", style = MaterialTheme.typography.titleLarge)
                }
            }

            Button(
                onClick = { isActive = !isActive },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isActive) "Pause Protection" else "Resume Protection")
            }
        }
    }
}
