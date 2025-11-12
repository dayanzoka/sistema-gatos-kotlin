package com.example.sistema_cuidadogatos.ui.screens.tutor

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
import com.example.sistema_cuidadogatos.database.entities.TutorEntity
import com.example.sistema_cuidadogatos.navigation.Screen
import com.example.sistema_cuidadogatos.ui.viewmodel.TutorViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorListScreen(
    navController: NavController,
    viewModel: TutorViewModel = koinViewModel()
) {
    val uiState by viewModel.tutorUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🧑‍💻 Lista de Tutores") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.TutorForm.route) }) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Tutor")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.tutors.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Nenhum tutor cadastrado. Adicione um novo!", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(uiState.tutors) { tutor ->
                    TutorListItem(tutor = tutor, onTutorClick = {
                        // Navega para a tela de detalhes (e permite editar)
                        navController.navigate(Screen.TutorForm.createRoute(tutor.id))
                    })
                }
            }
        }
    }
}

@Composable
fun TutorListItem(tutor: TutorEntity, onTutorClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onTutorClick),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(tutor.nome, style = MaterialTheme.typography.titleMedium)
            Text("Email: ${tutor.email}", style = MaterialTheme.typography.bodySmall)
            Text("Telefone: ${tutor.telefone}", style = MaterialTheme.typography.bodySmall)
        }
    }
}