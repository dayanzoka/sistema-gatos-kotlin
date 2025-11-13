package com.example.sistema_cuidadogatos.ui.screens.health

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.database.entities.RegistroSaudeEntity
import com.example.sistema_cuidadogatos.ui.viewmodel.RegistroSaudeViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthRecordFormScreen(
    navController: NavController,
    recordId: Long,
    gatoId: Long,
    viewModel: RegistroSaudeViewModel = koinViewModel()
) {
    var tipo by remember { mutableStateOf("PESO") }
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var dataStr by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())) }
    var valor by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Registro de Saúde") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dataLong = try {
                    dateFormat.parse(dataStr)?.time ?: System.currentTimeMillis()
                } catch (e: Exception) { System.currentTimeMillis() }

                val registro = RegistroSaudeEntity(
                    id = recordId,
                    gatoId = gatoId,
                    tipoRegistro = tipo,
                    titulo = titulo,
                    descricao = descricao,
                    data = dataLong,
                    valor = valor,
                    observacoes = observacoes
                )
                viewModel.saveRegistro(registro)
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Save, contentDescription = "Salvar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo (VACINA, PESO, ETC)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título (ex: Vacina Raiva)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = valor, onValueChange = { valor = it }, label = { Text("Valor (ex: 3.5kg, Dose 1)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = dataStr, onValueChange = { dataStr = it }, label = { Text("Data (dd/MM/yyyy)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = observacoes, onValueChange = { observacoes = it }, label = { Text("Observações") }, modifier = Modifier.fillMaxWidth())
        }
    }
}