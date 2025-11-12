package com.example.sistema_cuidadogatos.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tratamento",
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
data class TratamentoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val gatoId: Long, // FK
    val tipo: String, // ex: CONSULTA, VACINACAO, BANHO_TOSA
    val descricao: String,
    val dataAgendada: Long, // Timestamp
    val horario: String,
    val observacoes: String?,
    val status: String, // PENDENTE, REALIZADO, CANCELADO
    val dataCriacao: Long = System.currentTimeMillis()
)