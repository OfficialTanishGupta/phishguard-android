package com.phishguard.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Dashboard : BottomNavItem(
        route = "dashboard",
        title = "Dashboard",
        icon = Icons.Default.Home
    )

    object Activity : BottomNavItem(
        route = "activity",
        title = "Activity",
        icon = Icons.Default.List
    )

    object Apps : BottomNavItem(
        route = "apps",
        title = "Apps",
        icon = Icons.Default.Lock
    )

    object Settings : BottomNavItem(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}
