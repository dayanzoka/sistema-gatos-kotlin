package com.example.sistema_cuidadogatos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistema_cuidadogatos.database.entities.TutorEntity
import com.example.sistema_cuidadogatos.repository.CuiGatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TutorUiState(
    val tutors: List<TutorEntity> = emptyList(),
    val currentTutor: TutorEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class TutorViewModel(
    private val repository: CuiGatoRepository
) : ViewModel() {

    private val _tutorUiState = MutableStateFlow(TutorUiState(isLoading = true))
    val tutorUiState: StateFlow<TutorUiState> = _tutorUiState.asStateFlow()

    init {
        loadAllTutors()
    }

    private fun loadAllTutors() {
        viewModelScope.launch {
            _tutorUiState.update { it.copy(isLoading = true) }
            try {
                repository.getAllTutors().collect { tutorsList ->
                    _tutorUiState.update { it.copy(tutors = tutorsList, isLoading = false) }
                }
            } catch (e: Exception) {
                _tutorUiState.update { it.copy(error = "Erro ao carregar tutores: ${e.message}", isLoading = false) }
            }
        }
    }

    fun loadTutor(id: Long) {
        if (id == 0L) {
            _tutorUiState.update { it.copy(currentTutor = null) }
            return
        }
        viewModelScope.launch {
            val tutor = repository.getTutorById(id)
            _tutorUiState.update { it.copy(currentTutor = tutor) }
        }
    }

    fun saveTutor(tutor: TutorEntity) {
        viewModelScope.launch {
            repository.saveTutor(tutor)
        }
    }

    fun deleteTutor(tutor: TutorEntity) {
        viewModelScope.launch {
            repository.deleteTutor(tutor)
        }
    }
}