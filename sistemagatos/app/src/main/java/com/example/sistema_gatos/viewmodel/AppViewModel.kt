package com.example.sistema_gatos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sistema_gatos.data.local.Gato
import com.example.sistema_gatos.data.local.Tutor
import com.example.sistema_gatos.data.remote.PostDTO
import com.example.sistema_gatos.data.remote.UserDTO
import com.example.sistema_gatos.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppViewModel(private val repository: AppRepository) : ViewModel() {

    // Estados Local (Room)
    val tutores: StateFlow<List<Tutor>> = repository.todosTutores
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Gatos (filtrados ou todos)
    private val _gatosList = MutableStateFlow<List<Gato>>(emptyList())
    val gatosList: StateFlow<List<Gato>> = _gatosList.asStateFlow()

    fun carregarGatosDeTutor(tutorId: Long) {
        viewModelScope.launch {
            repository.getGatosPorTutor(tutorId).collect { _gatosList.value = it }
        }
    }

    fun adicionarTutor(tutor: Tutor) = viewModelScope.launch { repository.insertTutor(tutor) }
    fun deletarTutor(tutor: Tutor) = viewModelScope.launch { repository.deleteTutor(tutor) }
    fun adicionarGato(gato: Gato) = viewModelScope.launch { repository.insertGato(gato) }
    fun deletarGato(gato: Gato) = viewModelScope.launch { repository.deleteGato(gato) }

    // Estados Remoto (API)
    private val _apiUsers = MutableStateFlow<List<UserDTO>>(emptyList())
    val apiUsers = _apiUsers.asStateFlow()

    private val _apiResult = MutableStateFlow<String>("Aguardando requisição...")
    val apiResult = _apiResult.asStateFlow()

    fun buscarUsuariosAPI() {
        viewModelScope.launch {
            try {
                _apiUsers.value = repository.fetchApiUsers()
                _apiResult.value = "Sucesso: ${_apiUsers.value.size} usuários carregados."
            } catch (e: Exception) {
                _apiResult.value = "Erro: ${e.message}"
            }
        }
    }

    fun testarPostAPI() {
        viewModelScope.launch {
            try {
                val novoPost = PostDTO(1, null, "Teste CuiGato", "Enviando dados...")
                val resposta = repository.createApiPost(novoPost)
                _apiResult.value = "POST Sucesso! ID: ${resposta.id}, Título: ${resposta.title}"
            } catch (e: Exception) {
                _apiResult.value = "Erro POST: ${e.message}"
            }
        }
    }
}

// Factory para injetar o repositório
class AppViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}