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
import androidx.compose.ui.graphics.Color
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
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "⊹ ࣪ ˖ demonstração de api ⊹ ࣪ ˖ ",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("API: JSONPlaceholder", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { viewModel.fetchUsers() }, enabled = !state.isLoading, modifier = Modifier.weight(1f)) { Text("GET Users") }
                        Button(onClick = { viewModel.createPost() }, enabled = !state.isLoading, modifier = Modifier.weight(1f)) { Text("POST Post") }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { viewModel.updatePost() }, enabled = !state.isLoading, modifier = Modifier.weight(1f)) { Text("PUT Post") }
                        Button(onClick = { viewModel.deletePost() }, enabled = !state.isLoading, modifier = Modifier.weight(1f)) { Text("DELETE Post") }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Resultado:", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.CenterHorizontally))
                    } else {
                        Text(text = state.result, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}