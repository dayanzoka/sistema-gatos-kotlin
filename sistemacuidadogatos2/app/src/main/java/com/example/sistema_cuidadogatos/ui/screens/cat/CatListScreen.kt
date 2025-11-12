package com.example.sistema_cuidadogatos.ui.screens.cat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.ui.viewmodel.GatoViewModel
import com.example.sistema_cuidadogatos.database.entities.GatoEntity
import com.example.sistema_cuidadogatos.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatListScreen(
    navController: NavController,
    viewModel: GatoViewModel = koinViewModel()
) {
    val uiState by viewModel.gatoUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🐾 Lista de Gatos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.CatForm.route) }) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Gato")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.gatos.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Nenhum gato cadastrado.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(uiState.gatos) { gato ->
                    CatListItem(gato = gato, onCatClick = {
                        // Navega para a tela de detalhes do Gato
                        navController.navigate(Screen.CatDetail.createRoute(gato.id))
                    })
                }
            }
        }
    }
}

@Composable
fun CatListItem(gato: GatoEntity, onCatClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onCatClick),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(gato.nome, style = MaterialTheme.typography.titleMedium)
                Text("Raça: ${gato.raca} | Idade: ${gato.idade}", style = MaterialTheme.typography.bodySmall)
                Text("Peso: ${gato.peso} kg", style = MaterialTheme.typography.bodySmall)
            }
            // Aqui você poderia colocar a foto do gato, se não tivesse removido a opção.
        }
    }
}