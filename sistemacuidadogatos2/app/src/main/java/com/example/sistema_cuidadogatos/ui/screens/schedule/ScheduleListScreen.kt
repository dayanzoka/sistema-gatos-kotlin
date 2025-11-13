package com.example.sistema_cuidadogatos.ui.screens.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.database.entities.RegistroSaudeEntity
import com.example.sistema_cuidadogatos.database.entities.TratamentoEntity
import com.example.sistema_cuidadogatos.ui.viewmodel.GatoViewModel
import com.example.sistema_cuidadogatos.ui.viewmodel.RegistroSaudeViewModel
import com.example.sistema_cuidadogatos.ui.viewmodel.TratamentoViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleListScreen(
    navController: NavController,
    tratamentoViewModel: TratamentoViewModel = koinViewModel(),
    registroViewModel: RegistroSaudeViewModel = koinViewModel(),
    gatoViewModel: GatoViewModel = koinViewModel()
) {
    val tratamentoState by tratamentoViewModel.tratamentoUiState.collectAsState()
    val registroState by registroViewModel.registroSaudeUiState.collectAsState()
    val gatoState by gatoViewModel.gatoUiState.collectAsState()

    val catMap = remember(gatoState.gatos) {
        gatoState.gatos.associate { it.id to it.nome }
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Agendamentos", "Prontuário")

    LaunchedEffect(Unit) {
        tratamentoViewModel.loadAllTratamentos()
        registroViewModel.loadAllRegistros()
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Agenda e Histórico",
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
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary // Usa o Marrom do tema
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) },
                        icon = { Icon(if (index == 0) Icons.Default.Event else Icons.Default.History, null) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> AgendamentosTab(tratamentoState.tratamentos, catMap)
                1 -> ProntuarioTab(registroState.registros, catMap)
            }
        }
    }
}

@Composable
fun AgendamentosTab(tratamentos: List<TratamentoEntity>, catMap: Map<Long, String>) {
    if (tratamentos.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nenhum agendamento cadastrado.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tratamentos) { tratamento ->
                val catName = catMap[tratamento.gatoId] ?: "Gato #${tratamento.gatoId}"
                TratamentoCardGlobal(tratamento, catName)
            }
        }
    }
}

@Composable
fun ProntuarioTab(registros: List<RegistroSaudeEntity>, catMap: Map<Long, String>) {
    if (registros.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nenhum registro de saúde.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(registros) { registro ->
                val catName = catMap[registro.gatoId] ?: "Gato #${registro.gatoId}"
                RegistroSaudeCardGlobal(registro, catName)
            }
        }
    }
}

@Composable
fun TratamentoCardGlobal(tratamento: TratamentoEntity, catName: String) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val isDone = tratamento.status.equals("REALIZADO", ignoreCase = true)

    val cardBackground = if (isDone) Color(0xFF5D4037) else MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
    val textColor = if (isDone) Color(0xFFE5D7D6) else Color(0xFFE5D7D6)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardBackground,
            contentColor = textColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(tratamento.tipo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Badge(
                    containerColor = if (isDone) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                ) {
                    Text(if (isDone) "Concluído" else "Pendente", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("🐾 $catName", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Text("📅 ${dateFormatter.format(Date(tratamento.dataAgendada))} às ${tratamento.horario}")
            if (!tratamento.observacoes.isNullOrBlank()) {
                Text("Obs: ${tratamento.observacoes}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun RegistroSaudeCardGlobal(registro: RegistroSaudeEntity, catName: String) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Para o Prontuário, mantemos o padrão
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            contentColor = Color(0xFFE5D7D6)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(registro.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text("🐾 $catName", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Text("📝 Tipo: ${registro.tipoRegistro} | Data: ${dateFormatter.format(Date(registro.data))}")
            if (!registro.valor.isNullOrBlank()) {
                Text("⚖️ Valor: ${registro.valor}")
            }
        }
    }
}