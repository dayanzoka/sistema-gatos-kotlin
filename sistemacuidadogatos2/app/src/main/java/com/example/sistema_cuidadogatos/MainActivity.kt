package com.example.sistema_cuidadogatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sistema_cuidadogatos.navigation.Screen
import com.example.sistema_cuidadogatos.ui.screens.api_demo.ApiDemoScreen
import com.example.sistema_cuidadogatos.ui.screens.cat.CatDetailScreen
import com.example.sistema_cuidadogatos.ui.screens.cat.CatFormScreen
import com.example.sistema_cuidadogatos.ui.screens.cat.CatListScreen
import com.example.sistema_cuidadogatos.ui.screens.health.HealthHistoryScreen
import com.example.sistema_cuidadogatos.ui.screens.health.HealthRecordFormScreen
import com.example.sistema_cuidadogatos.ui.screens.home.HomeScreen
import com.example.sistema_cuidadogatos.ui.screens.schedule.ScheduleListScreen
import com.example.sistema_cuidadogatos.ui.screens.treatment.TreatmentFormScreen
import com.example.sistema_cuidadogatos.ui.screens.treatment.TreatmentListScreen
import com.example.sistema_cuidadogatos.ui.screens.tutor.TutorFormScreen
import com.example.sistema_cuidadogatos.ui.screens.tutor.TutorListScreen
import com.example.sistema_cuidadogatos.ui.theme.CuiGatoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CuiGatoTheme {
                // Box permite empilhar elementos (Fundo atrás, App na frente)
                Box(modifier = Modifier.fillMaxSize()) {

                    // 1. A Imagem de Fundo
                    Image(
                        painter = painterResource(id = R.drawable.wallpaper_background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // 2. O App (NavHost) com fundo transparente
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Transparent
                    ) {
                        CuiGatoAppNavHost() // <--- O ERRO ESTÁ AQUI SE A FUNÇÃO ABAIXO NÃO EXISTIR
                    }
                }
            }
        }
    }
}

// 👇 AQUI ESTÁ A PARTE QUE PROVAVELMENTE FALTA NO SEU ARQUIVO 👇
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

        // Rota de Detalhes do Gato
        composable(
            route = Screen.CatDetail.route,
            arguments = listOf(navArgument("gatoId") { type = NavType.LongType })
        ) { backStackEntry ->
            CatDetailScreen(
                navController = navController,
                gatoId = backStackEntry.arguments!!.getLong("gatoId")
            )
        }

        // Rotas de Tratamento
        composable(
            route = Screen.TreatmentList.route,
            arguments = listOf(navArgument("gatoId") { type = NavType.LongType })
        ) { backStackEntry ->
            TreatmentListScreen(
                navController = navController,
                gatoId = backStackEntry.arguments!!.getLong("gatoId")
            )
        }

        composable(
            route = Screen.TreatmentForm.route,
            arguments = listOf(
                navArgument("treatmentId") { defaultValue = 0L; type = NavType.LongType },
                navArgument("gatoId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            TreatmentFormScreen(
                navController = navController,
                treatmentId = backStackEntry.arguments!!.getLong("treatmentId"),
                gatoId = backStackEntry.arguments!!.getLong("gatoId")
            )
        }

        // Rotas de Saúde
        composable(
            route = Screen.HealthHistory.route,
            arguments = listOf(navArgument("gatoId") { type = NavType.LongType })
        ) { backStackEntry ->
            HealthHistoryScreen(
                navController = navController,
                gatoId = backStackEntry.arguments!!.getLong("gatoId")
            )
        }

        composable(
            route = Screen.HealthRecordForm.route,
            arguments = listOf(
                navArgument("recordId") { defaultValue = 0L; type = NavType.LongType },
                navArgument("gatoId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            HealthRecordFormScreen(
                navController = navController,
                recordId = backStackEntry.arguments!!.getLong("recordId"),
                gatoId = backStackEntry.arguments!!.getLong("gatoId")
            )
        }

        // Rota de Lista Geral de Agendamentos
        composable(Screen.ScheduleList.route) {
            ScheduleListScreen(navController = navController)
        }
    }
}