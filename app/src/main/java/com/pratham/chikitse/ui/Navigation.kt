package com.pratham.chikitse.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object EmergencyDetail : Screen("emergency/{id}") {
        fun createRoute(id: Int) = "emergency/$id"
    }
    object Search : Screen("search")
    object Hospitals : Screen("hospitals")
    object AiAssistant : Screen("ai_assistant")
    object Settings : Screen("settings")
}
