package com.example.sistema_cuidadogatos.ui.screens.cat

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.navigation.Screen
import com.example.sistema_cuidadogatos.ui.viewmodel.GatoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(
    navController: NavController,
    gatoId: Long,
    viewModel: GatoViewModel = koinViewModel()
) {
    // Carregar dados do gato
    var gato by remember { mutableStateOf<com.example.sistema_cuidadogatos.database.entities.GatoEntity?>(null) }

    LaunchedEffect(gatoId) {
        gato = viewModel.loadGato(gatoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(gato?.nome ?: "Detalhes do Gato") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.CatForm.createRoute(gatoId)) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                }
            )
        }
    ) { padding ->
        if (gato == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Cartão de Informações Básicas
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Informações", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Raça: ${gato!!.raca}")
                        Text("Idade: ${gato!!.idade} anos")
                        Text("Peso: ${gato!!.peso} kg")
                        Text("Cor: ${gato!!.cor}")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botões de Ação para Sub-funcionalidades
                Text("Ações Rápidas", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(Screen.TreatmentList.createRoute(gatoId)) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.MedicalServices, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tratamentos e Agendamentos")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(Screen.HealthHistory.createRoute(gatoId)) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(Icons.Default.History, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Histórico de Saúde")
                }
            }
        }
    }
}