package com.example.sistema_cuidadogatos.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "gato",
    foreignKeys = [
        ForeignKey(
            entity = TutorEntity::class,
            parentColumns = ["id"],
            childColumns = ["tutorId"],
            onDelete = ForeignKey.CASCADE // Deleção em cascata
        )
    ],
    indices = [Index(value = ["tutorId"])]
)
data class GatoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tutorId: Long, // Chave Estrangeira (FK)
    val nome: String,
    val raca: String,
    val idade: Int,
    val cor: String,
    val peso: Float,
    val dataCadastro: Long = System.currentTimeMillis()
)