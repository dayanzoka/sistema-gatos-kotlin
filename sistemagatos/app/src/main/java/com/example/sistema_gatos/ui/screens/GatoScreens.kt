package com.example.sistema_gatos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_gatos.data.local.Gato
import com.example.sistema_gatos.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GatoScreen(
    viewModel: AppViewModel,
    navController: NavController,
    tutorId: Long,
    tutorNome: String
) {
    // Carrega os gatos quando a tela abre
    LaunchedEffect(tutorId) {
        viewModel.carregarGatosDeTutor(tutorId)
    }

    val gatos by viewModel.gatosList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gatos de $tutorNome") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Gato")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(gatos) { gato ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nome: ${gato.nome}", style = MaterialTheme.typography.titleMedium)
                        Text("Raça: ${gato.raca} | Idade: ${gato.idade} anos")
                        Text("Peso: ${gato.peso} kg | Cor: ${gato.cor}")

                        Button(
                            onClick = { viewModel.deletarGato(gato) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Remover Gato")
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        GatoDialog(onDismiss = { showDialog = false }) { gato ->
            // Define o ID do tutor atual no novo gato
            viewModel.adicionarGato(gato.copy(tutorId = tutorId))
        }
    }
}

@Composable
fun GatoDialog(onDismiss: () -> Unit, onConfirm: (Gato) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var cor by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Gato") },
        text = {
            Column {
                OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })
                OutlinedTextField(value = raca, onValueChange = { raca = it }, label = { Text("Raça") })
                OutlinedTextField(value = cor, onValueChange = { cor = it }, label = { Text("Cor") })
                Row {
                    OutlinedTextField(
                        value = idade, onValueChange = { idade = it },
                        label = { Text("Idade") }, modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    OutlinedTextField(
                        value = peso, onValueChange = { peso = it },
                        label = { Text("Peso") }, modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (nome.isNotBlank()) {
                    onConfirm(
                        Gato(
                            tutorId = 0, // Será sobrescrito na tela
                            nome = nome, raca = raca, cor = cor,
                            idade = idade.toIntOrNull() ?: 0,
                            peso = peso.toFloatOrNull() ?: 0f
                        )
                    )
                    onDismiss()
                }
            }) { Text("Salvar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}