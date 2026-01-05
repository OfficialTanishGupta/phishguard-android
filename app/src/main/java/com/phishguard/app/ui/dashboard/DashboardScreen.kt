package com.phishguard.app.ui.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.phishguard.app.system.ProtectionService
import com.phishguard.app.system.UsagePermissionHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {

    val context = LocalContext.current
    var isProtectionEnabled by remember { mutableStateOf(false) }

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { }

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

            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Protection Status")
                    Text(
                        if (isProtectionEnabled) "ACTIVE" else "INACTIVE",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Button(
                onClick = {

                    // 1️⃣ Usage access permission
                    if (!UsagePermissionHelper.hasUsageAccess(context)) {
                        UsagePermissionHelper.openUsageAccessSettings(context)
                        return@Button
                    }

                    // 2️⃣ Notification permission (Android 13+)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (
                            context.checkSelfPermission(
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            notificationPermissionLauncher.launch(
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                            return@Button
                        }
                    }

                    val intent = Intent(context, ProtectionService::class.java)

                    if (!isProtectionEnabled) {
                        context.startForegroundService(intent)
                        isProtectionEnabled = true
                    } else {
                        context.stopService(intent)
                        isProtectionEnabled = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (isProtectionEnabled)
                        "Stop Background Protection"
                    else
                        "Start Background Protection"
                )
            }
        }
    }
}
