package com.example.sistema_gatos.data.remote

// Modelos de Dados da API
data class UserDTO(val id: Int, val name: String, val email: String)
data class PostDTO(val userId: Int, val id: Int?, val title: String, val body: String)