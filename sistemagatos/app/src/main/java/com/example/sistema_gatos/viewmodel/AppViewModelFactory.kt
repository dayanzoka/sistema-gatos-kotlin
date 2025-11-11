package com.example.sistema_gatos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sistema_gatos.repository.AppRepository


class AppViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Verifica se a classe que o sistema pediu é o AppViewModel
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {

            // Se for, cria ele passando o 'repository' que recebemos
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }

        // Se for um ViewModel desconhecido, lança um erro.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}