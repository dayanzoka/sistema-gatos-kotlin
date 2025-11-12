package com.example.sistema_cuidadogatos.ui.screens.tutor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var tutorToDelete by remember { mutableStateOf<TutorEntity?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

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
            FloatingActionButton(onClick = {
                navController.navigate(Screen.TutorForm.createRoute(0L))
            }) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 8.dp)
            ) {
                items(uiState.tutors) { tutor ->
                    TutorListItem(
                        tutor = tutor,
                        onEditClick = {
                            navController.navigate(Screen.TutorForm.createRoute(tutor.id))
                        },
                        onDeleteClick = {
                            tutorToDelete = tutor
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }

        // Dialog de confirmação de exclusão
        if (showDeleteDialog && tutorToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Confirmar Exclusão") },
                text = { Text("Tem certeza que deseja excluir ${tutorToDelete?.nome}?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            tutorToDelete?.let { viewModel.deleteTutor(it) }
                            showDeleteDialog = false
                            tutorToDelete = null
                        }
                    ) {
                        Text("Excluir", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun TutorListItem(
    tutor: TutorEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Informações do Tutor
            Column(modifier = Modifier.weight(1f)) {
                Text(tutor.nome, style = MaterialTheme.typography.titleMedium)
                Text("Email: ${tutor.email}", style = MaterialTheme.typography.bodySmall)
                Text("Telefone: ${tutor.telefone}", style = MaterialTheme.typography.bodySmall)
            }

            // Botões de Ação
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Botão Editar
                IconButton(
                    onClick = onEditClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Tutor")
                }

                // Botão Excluir
                IconButton(
                    onClick = onDeleteClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir Tutor")
                }
            }
        }
    }
}