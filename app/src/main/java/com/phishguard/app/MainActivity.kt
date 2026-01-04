package com.phishguard.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.phishguard.app.theme.PhishGuardTheme
import androidx.navigation.compose.rememberNavController
import com.phishguard.app.ui.navigation.*

import com.phishguard.app.ui.dashboard.DashboardScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhishGuardTheme {

                val navController = rememberNavController() // ✅ SINGLE SOURCE

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { padding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }

    }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhishGuardTheme {
        Greeting("Android")
    }
}