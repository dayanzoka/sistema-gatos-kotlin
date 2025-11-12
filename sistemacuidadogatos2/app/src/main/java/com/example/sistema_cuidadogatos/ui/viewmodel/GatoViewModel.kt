package com.example.sistema_cuidadogatos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistema_cuidadogatos.database.entities.GatoEntity
import com.example.sistema_cuidadogatos.repository.CuiGatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GatoUiState(
    val gatos: List<GatoEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class GatoViewModel(
    private val repository: CuiGatoRepository
) : ViewModel() {

    private val _gatoUiState = MutableStateFlow(GatoUiState(isLoading = true))
    val gatoUiState: StateFlow<GatoUiState> = _gatoUiState.asStateFlow()

    init {
        loadAllGatos()
    }

    private fun loadAllGatos() {
        viewModelScope.launch {
            _gatoUiState.update { it.copy(isLoading = true) }
            try {
                repository.getAllGatos().collect { gatosList ->
                    _gatoUiState.update { it.copy(gatos = gatosList, isLoading = false) }
                }
            } catch (e: Exception) {
                _gatoUiState.update { it.copy(error = "Erro ao carregar gatos: ${e.message}", isLoading = false) }
            }
        }
    }

    suspend fun loadGato(id: Long): GatoEntity? {
        return repository.getGatoById(id)
    }

    fun saveGato(gato: GatoEntity) {
        viewModelScope.launch {
            repository.saveGato(gato)
        }
    }

    fun deleteGato(gato: GatoEntity) {
        viewModelScope.launch {
            repository.deleteGato(gato)
        }
    }
}