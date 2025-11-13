package com.example.sistema_cuidadogatos.ui.screens.cat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var gatoToDelete by remember { mutableStateOf<GatoEntity?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "⊹ ࣪ ˖ lista de gatos ⊹ ࣪ ˖",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.CatForm.createRoute(0L))
            }) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 8.dp)
            ) {
                items(uiState.gatos) { gato ->
                    CatListItem(
                        gato = gato,
                        onViewClick = { navController.navigate(Screen.CatDetail.createRoute(gato.id)) },
                        onEditClick = { navController.navigate(Screen.CatForm.createRoute(gato.id)) },
                        onDeleteClick = {
                            gatoToDelete = gato
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }

        if (showDeleteDialog && gatoToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Confirmar Exclusão") },
                text = { Text("Tem certeza que deseja excluir ${gatoToDelete?.nome}?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            gatoToDelete?.let { viewModel.deleteGato(it) }
                            showDeleteDialog = false
                            gatoToDelete = null
                        }
                    ) { Text("Excluir", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}

@Composable
fun CatListItem(
    gato: GatoEntity,
    onViewClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(gato.nome, style = MaterialTheme.typography.titleMedium)
                Text("Raça: ${gato.raca} | Idade: ${gato.idade} anos", style = MaterialTheme.typography.bodySmall)
                Text("Peso: ${gato.peso} kg | Cor: ${gato.cor}", style = MaterialTheme.typography.bodySmall)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onViewClick) { Icon(Icons.Default.Visibility, "Ver", tint = MaterialTheme.colorScheme.primary) }
                IconButton(onClick = onEditClick) { Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary) }
                IconButton(onClick = onDeleteClick) { Icon(Icons.Default.Delete, "Excluir", tint = MaterialTheme.colorScheme.primary) }
            }
        }
    }
}