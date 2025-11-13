package com.example.sistema_cuidadogatos.ui.screens.treatment

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.database.entities.TratamentoEntity
import com.example.sistema_cuidadogatos.navigation.Screen
import com.example.sistema_cuidadogatos.ui.viewmodel.TratamentoViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentListScreen(
    navController: NavController,
    gatoId: Long,
    viewModel: TratamentoViewModel = koinViewModel()
) {
    val uiState by viewModel.tratamentoUiState.collectAsState()
    var tratamentoToDelete by remember { mutableStateOf<TratamentoEntity?>(null) }

    LaunchedEffect(gatoId) {
        viewModel.loadTratamentosByGato(gatoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("💊 Tratamentos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.TreatmentForm.createRoute(0L, gatoId))
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Novo Tratamento")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.tratamentos.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Nenhum tratamento agendado.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                items(uiState.tratamentos) { tratamento ->
                    TreatmentItem(
                        tratamento = tratamento,
                        onEdit = { navController.navigate(Screen.TreatmentForm.createRoute(tratamento.id, gatoId)) },
                        onDelete = { tratamentoToDelete = tratamento }
                    )
                }
            }
        }

        if (tratamentoToDelete != null) {
            AlertDialog(
                onDismissRequest = { tratamentoToDelete = null },
                title = { Text("Excluir Tratamento") },
                text = { Text("Deseja excluir este agendamento?") },
                confirmButton = {
                    TextButton(onClick = {
                        tratamentoToDelete?.let { viewModel.deleteTratamento(it) }
                        tratamentoToDelete = null
                    }) { Text("Excluir", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { tratamentoToDelete = null }) { Text("Cancelar") }
                }
            )
        }
    }
}

@Composable
fun TreatmentItem(
    tratamento: TratamentoEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(tratamento.tipo, style = MaterialTheme.typography.titleMedium)
                Text("Data: ${dateFormatter.format(Date(tratamento.dataAgendada))} às ${tratamento.horario}")
                Text("Status: ${tratamento.status}", style = MaterialTheme.typography.bodySmall,
                    color = if (tratamento.status == "REALIZADO") Color.Green else Color.Gray)
            }
            IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, null, tint = MaterialTheme.colorScheme.primary) }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error) }
        }
    }
}