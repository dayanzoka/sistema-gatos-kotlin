package com.example.sistema_cuidadogatos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistema_cuidadogatos.repository.CuiGatoRepository
import com.example.sistema_cuidadogatos.api.PostApiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

data class ApiDemoUiState(
    val result: String = "Clique em um botão para testar a API.",
    val isLoading: Boolean = false
)

class ApiDemoViewModel(
    private val repository: CuiGatoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ApiDemoUiState())
    val state: StateFlow<ApiDemoUiState> = _state.asStateFlow()

    private val json = Json { prettyPrint = true }

    fun fetchUsers() {
        executeApiCall {
            val response = repository.fetchUsersDemo()
            if (response.isSuccessful && response.body() != null) {
                "GET /users SUCESSO:\n${json.encodeToString(response.body())}"
            } else {
                "GET /users FALHA: Código ${response.code()}"
            }
        }
    }

    fun createPost() {
        executeApiCall {
            val newPost = PostApiModel(userId = 1, id = null, title = "Novo Post CuiGato", body = "Este é um teste de POST via Retrofit.")
            val response = repository.createPostDemo(newPost)
            if (response.isSuccessful && response.body() != null) {
                "POST /posts SUCESSO (Criado):\n${json.encodeToString(response.body())}"
            } else {
                "POST /posts FALHA: Código ${response.code()}"
            }
        }
    }

    fun updatePost() {
        executeApiCall {
            val updatedPost = PostApiModel(userId = 1, id = 1, title = "Post Atualizado CuiGato", body = "O corpo foi atualizado via PUT.")
            val response = repository.updatePostDemo(1, updatedPost)
            if (response.isSuccessful && response.body() != null) {
                "PUT /posts/1 SUCESSO (Atualizado):\n${json.encodeToString(response.body())}"
            } else {
                "PUT /posts/1 FALHA: Código ${response.code()}"
            }
        }
    }

    fun deletePost() {
        executeApiCall {
            val response = repository.deletePostDemo(1)
            if (response.isSuccessful) {
                "DELETE /posts/1 SUCESSO: Código ${response.code()} (Recurso removido)"
            } else {
                "DELETE /posts/1 FALHA: Código ${response.code()}"
            }
        }
    }

    private fun executeApiCall(call: suspend () -> String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, result = "Carregando...") }
            try {
                val resultString = call()
                _state.update { it.copy(result = resultString, isLoading = false) }
            } catch (e: IOException) {
                _state.update { it.copy(result = "ERRO DE REDE: ${e.message}", isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(result = "ERRO DESCONHECIDO: ${e.message}", isLoading = false) }
            }
        }
    }
}