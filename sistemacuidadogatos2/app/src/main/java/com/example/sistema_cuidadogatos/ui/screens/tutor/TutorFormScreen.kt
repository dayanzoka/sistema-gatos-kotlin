package com.example.sistema_cuidadogatos.ui.screens.tutor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.database.entities.TutorEntity
import com.example.sistema_cuidadogatos.ui.viewmodel.TutorViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorFormScreen(
    navController: NavController,
    tutorId: Long,
    viewModel: TutorViewModel = koinViewModel()
) {
    val isEdit = tutorId != 0L

    // Opcional: Carregar dados do tutor se for edição
    LaunchedEffect(tutorId) {
        viewModel.loadTutor(tutorId)
    }

    val currentTutor = viewModel.tutorUiState.collectAsState().value.currentTutor
    // Inicializa os campos com dados existentes ou vazios
    var nome by remember { mutableStateOf(currentTutor?.nome ?: "") }
    var email by remember { mutableStateOf(currentTutor?.email ?: "") }
    var telefone by remember { mutableStateOf(currentTutor?.telefone ?: "") }
    var endereco by remember { mutableStateOf(currentTutor?.endereco ?: "") }

    // Atualiza os campos se o currentTutor mudar (após carregar o tutor para edição)
    LaunchedEffect(currentTutor) {
        currentTutor?.let {
            nome = it.nome
            email = it.email
            telefone = it.telefone
            endereco = it.endereco
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Editar Tutor" else "Novo Tutor") },
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
                    if (nome.isNotBlank() && email.isNotBlank()) {
                        val tutor = TutorEntity(
                            id = if (isEdit) tutorId else 0L,
                            nome = nome,
                            email = email,
                            telefone = telefone,
                            endereco = endereco,
                            dataCadastro = currentTutor?.dataCadastro ?: System.currentTimeMillis()
                        )

                        viewModel.saveTutor(tutor)
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
        ) {
            OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = telefone, onValueChange = { telefone = it }, label = { Text("Telefone") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = endereco, onValueChange = { endereco = it }, label = { Text("Endereço") }, modifier = Modifier.fillMaxWidth())
        }
    }
}