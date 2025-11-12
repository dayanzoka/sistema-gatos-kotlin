package com.example.sistema_cuidadogatos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistema_cuidadogatos.database.entities.RegistroSaudeEntity
import com.example.sistema_cuidadogatos.repository.CuiGatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistroSaudeUiState(
    val registros: List<RegistroSaudeEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class RegistroSaudeViewModel(
    private val repository: CuiGatoRepository
) : ViewModel() {

    private val _registroSaudeUiState = MutableStateFlow(RegistroSaudeUiState(isLoading = false))
    val registroSaudeUiState: StateFlow<RegistroSaudeUiState> = _registroSaudeUiState.asStateFlow()

    // O carregamento inicial pode ser feito sob demanda (por gatoId)

    fun loadRegistrosByGato(gatoId: Long) {
        viewModelScope.launch {
            _registroSaudeUiState.update { it.copy(isLoading = true) }
            try {
                repository.getRegistrosByGato(gatoId).collect { lista ->
                    _registroSaudeUiState.update { it.copy(registros = lista, isLoading = false) }
                }
            } catch (e: Exception) {
                _registroSaudeUiState.update { it.copy(error = "Erro ao carregar registros: ${e.message}", isLoading = false) }
            }
        }
    }

    fun saveRegistro(registro: RegistroSaudeEntity) {
        viewModelScope.launch {
            repository.saveRegistroSaude(registro)
        }
    }

    fun deleteRegistro(registro: RegistroSaudeEntity) {
        viewModelScope.launch {
            repository.deleteRegistroSaude(registro)
        }
    }
}