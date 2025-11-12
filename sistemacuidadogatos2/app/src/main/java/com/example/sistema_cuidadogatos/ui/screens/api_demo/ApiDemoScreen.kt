package com.example.sistema_cuidadogatos.ui.screens.api_demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.ui.viewmodel.ApiDemoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiDemoScreen(
    navController: NavController,
    viewModel: ApiDemoViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("🌐 Demonstração API") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("API: JSONPlaceholder (Apenas para Teste de Rede)", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Botões de Ação
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { viewModel.fetchUsers() },
                    enabled = !state.isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("GET /users")
                }
                Button(
                    onClick = { viewModel.createPost() },
                    enabled = !state.isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("POST /posts")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Mais botões de demonstração (PUT, DELETE)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { viewModel.updatePost() },
                    enabled = !state.isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("PUT /posts/1")
                }
                Button(
                    onClick = { viewModel.deletePost() },
                    enabled = !state.isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("DELETE /posts/1")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Exibição do Resultado
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Resultado da Requisição:", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.CenterHorizontally))
                    } else {
                        // Usando um componente para exibir texto pré-formatado
                        Text(
                            text = state.result,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

