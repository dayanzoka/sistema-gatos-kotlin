package com.example.sistema_cuidadogatos.ui.screens.cat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.database.entities.GatoEntity
import com.example.sistema_cuidadogatos.ui.viewmodel.GatoViewModel
import com.example.sistema_cuidadogatos.ui.viewmodel.TutorViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatFormScreen(
    navController: NavController,
    gatoId: Long,
    gatoViewModel: GatoViewModel = koinViewModel(),
    tutorViewModel: TutorViewModel = koinViewModel()
) {
    val isEdit = gatoId != 0L

    // Lista de tutores para seleção
    val tutorsState by tutorViewModel.tutorUiState.collectAsState()
    val availableTutors = tutorsState.tutors

    // Variáveis de estado
    var nome by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var cor by remember { mutableStateOf("") }
    var selectedTutorId by remember { mutableStateOf(0L) }

    // Carregar gato para edição
    LaunchedEffect(gatoId) {
        if (isEdit) {
            val gato = gatoViewModel.loadGato(gatoId)
            gato?.let {
                nome = it.nome
                raca = it.raca
                idade = it.idade.toString()
                peso = it.peso.toString()
                cor = it.cor
                selectedTutorId = it.tutorId
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Editar Gato" else "Novo Gato") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (selectedTutorId != 0L && nome.isNotBlank()) {
                        val gato = GatoEntity(
                            id = gatoId,
                            tutorId = selectedTutorId,
                            nome = nome,
                            raca = raca,
                            idade = idade.toIntOrNull() ?: 0,
                            cor = cor,
                            peso = peso.toFloatOrNull() ?: 0f
                        )
                        gatoViewModel.saveGato(gato)
                        navController.popBackStack()
                    }
                },
                icon = { Icon(Icons.Filled.Save, contentDescription = "Salvar") },
                text = { Text("Salvar") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Seleção de Tutor (Dropdown Menu)
            if (availableTutors.isNotEmpty()) {
                var expanded by remember { mutableStateOf(false) }
                val selectedTutorName = availableTutors.find { it.id == selectedTutorId }?.nome ?: "Selecione um Tutor"

                OutlinedTextField(
                    value = selectedTutorName,
                    onValueChange = {},
                    label = { Text("Tutor") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Selecionar Tutor")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    availableTutors.forEach { tutor ->
                        DropdownMenuItem(
                            text = { Text(tutor.nome) },
                            onClick = {
                                selectedTutorId = tutor.id
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                Text("Nenhum tutor cadastrado. Cadastre um antes.", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = raca, onValueChange = { raca = it }, label = { Text("Raça") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = idade, onValueChange = { idade = it.filter { it.isDigit() } }, label = { Text("Idade (Anos)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = peso, onValueChange = { peso = it.filter { it.isDigit() || it == '.' } }, label = { Text("Peso (kg)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = cor, onValueChange = { cor = it }, label = { Text("Cor") }, modifier = Modifier.fillMaxWidth())
        }
    }
}
