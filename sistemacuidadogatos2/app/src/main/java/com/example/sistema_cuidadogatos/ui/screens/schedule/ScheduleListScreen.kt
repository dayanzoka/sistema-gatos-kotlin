package com.example.sistema_cuidadogatos.ui.screens.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.ui.viewmodel.TratamentoViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleListScreen(
    navController: NavController,
    viewModel: TratamentoViewModel = koinViewModel()
) {
    val uiState by viewModel.tratamentoUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAllTratamentos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🗓️ Todos os Agendamentos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else if (uiState.tratamentos.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Nenhum agendamento encontrado.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)
            ) {
                // Agrupando por status ou data se desejado, aqui lista simples
                items(uiState.tratamentos) { tratamento ->
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (tratamento.status == "REALIZADO") Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(tratamento.tipo, style = MaterialTheme.typography.titleMedium)
                            Text("Data: ${dateFormat.format(Date(tratamento.dataAgendada))} às ${tratamento.horario}")
                            Text("Status: ${tratamento.status}", style = MaterialTheme.typography.bodySmall)
                            if (!tratamento.observacoes.isNullOrBlank()) {
                                Text("Obs: ${tratamento.observacoes}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}