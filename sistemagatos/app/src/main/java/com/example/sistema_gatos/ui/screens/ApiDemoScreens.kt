package com.example.sistema_gatos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_gatos.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiDemoScreen(viewModel: AppViewModel, navController: NavController) {
    val status by viewModel.apiResult.collectAsState()
    val users by viewModel.apiUsers.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Integração API (JSONPlaceholder)") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Status: $status", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.buscarUsuariosAPI() }, modifier = Modifier.weight(1f)) {
                    Text("GET Users")
                }
                Button(onClick = { viewModel.testarPostAPI() }, modifier = Modifier.weight(1f)) {
                    Text("POST Teste")
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            LazyColumn {
                items(users) { user ->
                    ListItem(
                        headlineContent = { Text(user.name) },
                        supportingContent = { Text(user.email) },
                        leadingContent = { Text("#${user.id}") }
                    )
                    Divider()
                }
            }
        }
    }
}