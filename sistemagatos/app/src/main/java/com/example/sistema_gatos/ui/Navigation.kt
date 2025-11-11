package com.example.sistema_gatos.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sistema_gatos.ui.screens.*
import com.example.sistema_gatos.viewmodel.AppViewModel

@Composable
fun AppNavigation(viewModel: AppViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }

        composable("tutores") {
            TutorScreen(viewModel, navController)
        }

        // Rota para ver gatos de um tutor específico
        composable(
            "gatos/{tutorId}/{tutorNome}",
            arguments = listOf(
                navArgument("tutorId") { type = NavType.LongType },
                navArgument("tutorNome") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getLong("tutorId") ?: 0L
            val tutorNome = backStackEntry.arguments?.getString("tutorNome") ?: ""
            GatoScreen(viewModel, navController, tutorId, tutorNome)
        }

        composable("api_demo") { ApiDemoScreen(viewModel, navController) }
    }
}