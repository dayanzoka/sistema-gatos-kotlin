package com.example.sistema_cuidadogatos.ui.screens.treatment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.database.entities.TratamentoEntity
import com.example.sistema_cuidadogatos.ui.viewmodel.TratamentoViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentFormScreen(
    navController: NavController,
    treatmentId: Long,
    gatoId: Long,
    viewModel: TratamentoViewModel = koinViewModel()
) {
    val isEdit = treatmentId != 0L
    var tipo by remember { mutableStateOf("CONSULTA") }
    var descricao by remember { mutableStateOf("") }
    var dataStr by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())) }
    var horario by remember { mutableStateOf("09:00") }
    var status by remember { mutableStateOf("PENDENTE") }
    var observacoes by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Transparent, // ✅ Transparente
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Editar Tratamento" else "Novo Tratamento") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent) // ✅ Transparente
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dataLong = try {
                    dateFormat.parse(dataStr)?.time ?: System.currentTimeMillis()
                } catch (e: Exception) { System.currentTimeMillis() }

                val tratamento = TratamentoEntity(
                    id = treatmentId,
                    gatoId = gatoId,
                    tipo = tipo,
                    descricao = descricao,
                    dataAgendada = dataLong,
                    horario = horario,
                    status = status,
                    observacoes = observacoes
                )
                viewModel.saveTratamento(tratamento)
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Save, contentDescription = "Salvar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo (ex: VACINA)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = dataStr, onValueChange = { dataStr = it }, label = { Text("Data (dd/MM/yyyy)") }, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = horario, onValueChange = { horario = it }, label = { Text("Horário") }, modifier = Modifier.weight(1f))
                    }
                    OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = observacoes, onValueChange = { observacoes = it }, label = { Text("Obs") }, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}