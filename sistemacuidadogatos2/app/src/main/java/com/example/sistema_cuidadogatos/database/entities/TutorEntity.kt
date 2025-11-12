package com.example.sistema_cuidadogatos.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tutor")
data class TutorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nome: String,
    val email: String,
    val telefone: String,
    val endereco: String,
    val dataCadastro: Long = System.currentTimeMillis()
)