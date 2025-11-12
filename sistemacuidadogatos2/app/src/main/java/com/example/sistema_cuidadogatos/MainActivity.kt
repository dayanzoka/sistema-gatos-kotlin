package com.example.sistema_cuidadogatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sistema_cuidadogatos.navigation.Screen
import com.example.sistema_cuidadogatos.ui.screens.api_demo.ApiDemoScreen
import com.example.sistema_cuidadogatos.ui.screens.cat.CatFormScreen
import com.example.sistema_cuidadogatos.ui.screens.cat.CatListScreen
import com.example.sistema_cuidadogatos.ui.screens.home.HomeScreen
import com.example.sistema_cuidadogatos.ui.screens.tutor.TutorFormScreen
import com.example.sistema_cuidadogatos.ui.screens.tutor.TutorListScreen
import com.example.sistema_cuidadogatos.ui.theme.CuiGatoTheme
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CuiGatoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CuiGatoAppNavHost()
                }
            }
        }
    }
}

@Composable
fun CuiGatoAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Rotas Principais
        composable(Screen.Home.route) { HomeScreen(navController = navController) }
        composable(Screen.ApiDemo.route) { ApiDemoScreen(navController = navController) }

        // Rotas de Tutor
        composable(Screen.TutorList.route) { TutorListScreen(navController = navController) }
        composable(
            route = Screen.TutorForm.route,
            arguments = listOf(navArgument("tutorId") { defaultValue = 0L; type = NavType.LongType })
        ) { backStackEntry ->
            TutorFormScreen(
                navController = navController,
                tutorId = backStackEntry.arguments?.getLong("tutorId") ?: 0L
            )
        }

        // Rotas de Gato
        composable(Screen.CatList.route) { CatListScreen(navController = navController) }
        composable(
            route = Screen.CatForm.route,
            arguments = listOf(navArgument("gatoId") { defaultValue = 0L; type = NavType.LongType })
        ) { backStackEntry ->
            CatFormScreen(
                navController = navController,
                gatoId = backStackEntry.arguments?.getLong("gatoId") ?: 0L
            )
        }

        // Rota de Detalhes do Gato (Onde o usuário acessa saúde e tratamentos)
        composable(
            route = Screen.CatDetail.route,
            arguments = listOf(navArgument("gatoId") { type = NavType.LongType })
        ) {
            // CatDetailScreen(navController = navController, gatoId = it.arguments!!.getLong("gatoId"))
            Text("Detalhes do Gato ID: ${it.arguments!!.getLong("gatoId")}")
        }


        // Rotas de Tratamento
        composable(
            route = Screen.TreatmentList.route,
            arguments = listOf(navArgument("gatoId") { type = NavType.LongType })
        ) {
            // TreatmentListScreen(navController = navController, gatoId = it.arguments!!.getLong("gatoId"))
            Text("Lista de Tratamentos para Gato ID: ${it.arguments!!.getLong("gatoId")}")
        }

        composable(
            route = Screen.TreatmentForm.route,
            arguments = listOf(
                navArgument("treatmentId") { defaultValue = 0L; type = NavType.LongType },
                navArgument("gatoId") { type = NavType.LongType }
            )
        ) {
            // TreatmentFormScreen(navController = navController, treatmentId = it.arguments!!.getLong("treatmentId"), gatoId = it.arguments!!.getLong("gatoId"))
            Text("Formulário Tratamento Gato ID: ${it.arguments!!.getLong("gatoId")}")
        }


        // Rotas de Saúde
        composable(
            route = Screen.HealthHistory.route,
            arguments = listOf(navArgument("gatoId") { type = NavType.LongType })
        ) {
            // HealthHistoryScreen(navController = navController, gatoId = it.arguments!!.getLong("gatoId"))
            Text("Histórico de Saúde para Gato ID: ${it.arguments!!.getLong("gatoId")}")
        }

        composable(
            route = Screen.HealthRecordForm.route,
            arguments = listOf(
                navArgument("recordId") { defaultValue = 0L; type = NavType.LongType },
                navArgument("gatoId") { type = NavType.LongType }
            )
        ) {
            // HealthRecordFormScreen(navController = navController, recordId = it.arguments!!.getLong("recordId"), gatoId = it.arguments!!.getLong("gatoId"))
            Text("Formulário Registro Saúde Gato ID: ${it.arguments!!.getLong("gatoId")}")
        }
    }
}