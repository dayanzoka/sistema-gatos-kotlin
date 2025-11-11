package com.example.sistema_gatos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_gatos.data.local.Tutor
import com.example.sistema_gatos.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorScreen(viewModel: AppViewModel, navController: NavController) {
    val tutores by viewModel.tutores.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tutores") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(tutores) { tutor ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Navega para a tela de gatos deste tutor
                            navController.navigate("gatos/${tutor.id}/${tutor.nome}")
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = tutor.nome, style = MaterialTheme.typography.titleMedium)
                            Text(text = tutor.email, style = MaterialTheme.typography.bodyMedium)
                        }
                        IconButton(onClick = { viewModel.deletarTutor(tutor) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = MaterialTheme.colorScheme.error)
                        }
                        Icon(Icons.Default.Pets, contentDescription = "Gatos")
                    }
                }
            }
        }
    }

    if (showDialog) {
        TutorDialog(onDismiss = { showDialog = false }, onConfirm = { t -> viewModel.adicionarTutor(t) })
    }
}

@Composable
fun TutorDialog(onDismiss: () -> Unit, onConfirm: (Tutor) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Novo Tutor") },
        text = {
            Column {
                OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(value = telefone, onValueChange = { telefone = it }, label = { Text("Telefone") })
            }
        },
        confirmButton = {
            Button(onClick = {
                if (nome.isNotBlank()) {
                    onConfirm(Tutor(nome = nome, email = email, telefone = telefone, endereco = ""))
                    onDismiss()
                }
            }) { Text("Salvar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}