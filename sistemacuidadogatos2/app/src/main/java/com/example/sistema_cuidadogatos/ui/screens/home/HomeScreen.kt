package com.example.sistema_cuidadogatos.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home - CuiGato") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bem-vindo ao Sistema de Cuidados Felinos!", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(32.dp))

            // Cards de Acesso Rápido (Funcionalidades Principais)
            HomeFeatureCard(
                title = "Gerenciar Tutores",
                description = "Cadastro, edição e lista de responsáveis.",
                icon = Icons.Default.Person,
                onClick = { navController.navigate(Screen.TutorList.route) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            HomeFeatureCard(
                title = "Gerenciar Gatos",
                description = "Informações, saúde e tratamentos dos seus pets.",
                icon = Icons.Default.Pets, // Exemplo de ícone, use um emoji ou ícone apropriado.
                onClick = { navController.navigate(Screen.CatList.route) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            HomeFeatureCard(
                title = "Agendamentos/Histórico",
                description = "Visualize e agende tratamentos e serviços.",
                icon = Icons.Default.CalendarToday,
                onClick = { /* Navegar para a lista de tratamentos */ }
            )
            Spacer(modifier = Modifier.height(16.dp))

            HomeFeatureCard(
                title = "Demonstração API",
                description = "Teste de requisições GET, POST, PUT, DELETE.",
                icon = Icons.Default.List,
                onClick = { navController.navigate(Screen.ApiDemo.route) }
            )
        }
    }
}

@Composable
fun HomeFeatureCard(
    title: String,
    description: String,
    icon: Any, // Usando Any para aceitar Vector/Image assets (simplificado)
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Renderiza o ícone
            if (icon is androidx.compose.ui.graphics.vector.ImageVector) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}