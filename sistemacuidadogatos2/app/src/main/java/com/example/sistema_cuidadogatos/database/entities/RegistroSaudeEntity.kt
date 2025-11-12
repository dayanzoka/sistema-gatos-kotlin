package com.example.sistema_cuidadogatos.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "registro_saude",
    foreignKeys = [
        ForeignKey(
            entity = GatoEntity::class,
            parentColumns = ["id"],
            childColumns = ["gatoId"],
            onDelete = ForeignKey.CASCADE // Deleção em cascata
        )
    ],
    indices = [Index(value = ["gatoId"])]
)
data class RegistroSaudeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val gatoId: Long, // FK
    val tipoRegistro: String, // ex: VACINA, PESO, ALERGIA, MEDICAMENTO
    val titulo: String,
    val descricao: String,
    val data: Long, // Timestamp do registro
    val valor: String?, // ex: "3.5kg", "5ml"
    val observacoes: String?
)