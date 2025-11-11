package com.example.sistema_gatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.sistema_gatos.data.local.AppDatabase
import com.example.sistema_gatos.data.remote.RetrofitClient
import com.example.sistema_gatos.repository.AppRepository
import com.example.sistema_gatos.ui.AppNavigation
import com.example.sistema_gatos.viewmodel.AppViewModel
import com.example.sistema_gatos.viewmodel.AppViewModelFactory
// Importe o tema gerado automaticamente pelo seu projeto, ex:
// import com.example.sistema_para_gatos.ui.theme.SistemaParaGatosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Banco de Dados
        val database = AppDatabase.getDatabase(this)

        // Inicializa API
        val apiService = RetrofitClient.apiService

        // Inicializa Repositório
        val repository = AppRepository(database.appDao(), apiService)

        // Inicializa ViewModel
        val viewModelFactory = AppViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]

        setContent {
            // SistemaParaGatosTheme { // Descomente se tiver o tema configurado
            AppNavigation(viewModel = viewModel)
            // }
        }
    }
}