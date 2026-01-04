package com.phishguard.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phishguard.app.ui.dashboard.DashboardScreen
import com.phishguard.app.ui.apps.AppsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Dashboard.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Dashboard.route) {
            DashboardScreen()
        }
        composable(BottomNavItem.Activity.route) {
            PlaceholderScreen("Activity")
        }
        composable(BottomNavItem.Apps.route) {
            AppsScreen()
        }
        composable(BottomNavItem.Settings.route) {
            PlaceholderScreen("Settings")
        }
    }
}
