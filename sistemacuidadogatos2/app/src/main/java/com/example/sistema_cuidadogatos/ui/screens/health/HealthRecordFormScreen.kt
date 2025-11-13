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
import androidx.compose.ui.graphics.Color
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
        containerColor = Color.Transparent, // ✅ Transparente
        topBar = {
            TopAppBar(
                title = { Text("Novo Registro de Saúde") },
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
            modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = valor, onValueChange = { valor = it }, label = { Text("Valor") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = dataStr, onValueChange = { dataStr = it }, label = { Text("Data") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = observacoes, onValueChange = { observacoes = it }, label = { Text("Obs") }, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}