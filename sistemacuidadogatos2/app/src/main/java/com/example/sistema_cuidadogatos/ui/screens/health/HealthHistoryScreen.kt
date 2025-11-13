package com.example.sistema_cuidadogatos.ui.screens.health

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.database.entities.RegistroSaudeEntity
import com.example.sistema_cuidadogatos.navigation.Screen
import com.example.sistema_cuidadogatos.ui.viewmodel.RegistroSaudeViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthHistoryScreen(
    navController: NavController,
    gatoId: Long,
    viewModel: RegistroSaudeViewModel = koinViewModel()
) {
    val uiState by viewModel.registroSaudeUiState.collectAsState()
    var recordToDelete by remember { mutableStateOf<RegistroSaudeEntity?>(null) }

    LaunchedEffect(gatoId) {
        viewModel.loadRegistrosByGato(gatoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🏥 Histórico de Saúde") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.HealthRecordForm.createRoute(0L, gatoId))
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Registro")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else if (uiState.registros.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Nenhum registro de saúde encontrado.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)
            ) {
                items(uiState.registros) { registro ->
                    HealthRecordItem(
                        registro = registro,
                        onDelete = { recordToDelete = registro }
                    )
                }
            }
        }

        if (recordToDelete != null) {
            AlertDialog(
                onDismissRequest = { recordToDelete = null },
                title = { Text("Excluir Registro") },
                text = { Text("Deseja excluir este registro de saúde?") },
                confirmButton = {
                    TextButton(onClick = {
                        recordToDelete?.let { viewModel.deleteRegistro(it) }
                        recordToDelete = null
                    }) { Text("Excluir", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = { TextButton(onClick = { recordToDelete = null }) { Text("Cancelar") } }
            )
        }
    }
}

@Composable
fun HealthRecordItem(registro: RegistroSaudeEntity, onDelete: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(registro.titulo, style = MaterialTheme.typography.titleMedium)
                Text("Tipo: ${registro.tipoRegistro} | Data: ${dateFormat.format(Date(registro.data))}")
                if (!registro.valor.isNullOrBlank()) Text("Valor: ${registro.valor}")
            }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error) }
        }
    }
}