package com.example.sistema_cuidadogatos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistema_cuidadogatos.database.entities.TratamentoEntity
import com.example.sistema_cuidadogatos.repository.CuiGatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TratamentoUiState(
    val tratamentos: List<TratamentoEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class TratamentoViewModel(
    private val repository: CuiGatoRepository
) : ViewModel() {

    private val _tratamentoUiState = MutableStateFlow(TratamentoUiState(isLoading = true))
    val tratamentoUiState: StateFlow<TratamentoUiState> = _tratamentoUiState.asStateFlow()

    init {
        // Opção: Não carregar nada no init se o uso for sempre filtrado por gato.
        // loadAllTratamentos()
    }

    fun loadAllTratamentos() {
        viewModelScope.launch {
            _tratamentoUiState.update { it.copy(isLoading = true) }
            try {
                repository.getAllTratamentos().collect { lista ->
                    _tratamentoUiState.update { it.copy(tratamentos = lista, isLoading = false) }
                }
            } catch (e: Exception) {
                _tratamentoUiState.update { it.copy(error = "Erro ao carregar tratamentos: ${e.message}", isLoading = false) }
            }
        }
    }

    fun loadTratamentosByGato(gatoId: Long) {
        viewModelScope.launch {
            _tratamentoUiState.update { it.copy(isLoading = true) }
            try {
                repository.getTratamentosByGato(gatoId).collect { lista ->
                    _tratamentoUiState.update { it.copy(tratamentos = lista, isLoading = false) }
                }
            } catch (e: Exception) {
                _tratamentoUiState.update { it.copy(error = "Erro ao carregar tratamentos: ${e.message}", isLoading = false) }
            }
        }
    }

    fun saveTratamento(tratamento: TratamentoEntity) {
        viewModelScope.launch {
            repository.saveTratamento(tratamento)
        }
    }

    fun deleteTratamento(tratamento: TratamentoEntity) {
        viewModelScope.launch {
            repository.deleteTratamento(tratamento)
        }
    }
}