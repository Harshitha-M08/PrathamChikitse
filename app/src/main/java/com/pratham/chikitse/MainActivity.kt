package com.pratham.chikitse

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.pratham.chikitse.ui.Screen
import com.pratham.chikitse.ui.ai.AiAssistantScreen
import com.pratham.chikitse.ui.emergency.EmergencyDetailScreen
import com.pratham.chikitse.ui.home.HomeScreen
import com.pratham.chikitse.ui.hospital.HospitalScreen
import com.pratham.chikitse.ui.search.SearchScreen
import com.pratham.chikitse.ui.settings.SettingsScreen
import com.pratham.chikitse.ui.settings.SettingsViewModel
import com.pratham.chikitse.ui.theme.PrathamChikitseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           // Log masked GROQ_API_KEY at startup for debugging API key issues
        try {
              val key = com.pratham.chikitse.BuildConfig.GROQ_API_KEY
            val masked = if (key.length > 8) key.substring(0, 8) + "..." else key
              Log.d("PrathamChikitse", "GROQ_API_KEY (masked): $masked")
        } catch (e: Exception) {
              Log.d("PrathamChikitse", "GROQ_API_KEY not available: ${e.localizedMessage}")
        }
        setContent {
            val settingsVm: SettingsViewModel = hiltViewModel()
            val darkMode by settingsVm.darkMode.collectAsState()

            PrathamChikitseTheme(darkTheme = darkMode) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(onNavigate = { route -> navController.navigate(route) })
                    }

                    composable(
                        route = Screen.EmergencyDetail.route,
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) {
                        EmergencyDetailScreen(onBack = { navController.popBackStack() })
                    }

                    composable(Screen.Search.route) {
                        SearchScreen(
                            onNavigate = { route -> navController.navigate(route) },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.Hospitals.route) {
                        HospitalScreen(onBack = { navController.popBackStack() })
                    }

                    composable(Screen.AiAssistant.route) {
                        AiAssistantScreen(onBack = { navController.popBackStack() })
                    }

                    composable(Screen.Settings.route) {
                        SettingsScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
