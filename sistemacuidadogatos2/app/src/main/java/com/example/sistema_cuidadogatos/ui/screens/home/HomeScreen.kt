package com.example.sistema_cuidadogatos.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistema_cuidadogatos.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.Transparent,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "sistema de cuidado \n para gatos ฅ^•ﻌ•^ฅ", // Você pode mudar o texto aqui
                style = MaterialTheme.typography.titleMedium, // ✅ Mudei para titleMedium (menor)
                color = MaterialTheme.colorScheme.onSurface, // Garante que fique Marrom
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Cards de Acesso Rápido
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
                icon = Icons.Default.Pets,
                onClick = { navController.navigate(Screen.CatList.route) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            HomeFeatureCard(
                title = "Agendamentos/Histórico",
                description = "Visualize e agende tratamentos e serviços.",
                icon = Icons.Default.CalendarToday,
                onClick = { navController.navigate(Screen.ScheduleList.route) }
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
    icon: Any,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon is ImageVector) {
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