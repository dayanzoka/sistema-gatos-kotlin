package com.example.sistema_gatos.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tutores")
data class Tutor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val email: String,
    val telefone: String,
    val endereco: String,
    val dataCadastro: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "gatos",
    foreignKeys = [ForeignKey(
        entity = Tutor::class,
        parentColumns = ["id"],
        childColumns = ["tutorId"],
        onDelete = ForeignKey.CASCADE // Se apagar o tutor, apaga os gatos
    )]
)
data class Gato(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tutorId: Long, // Chave estrangeira
    val nome: String,
    val raca: String,
    val idade: Int,
    val cor: String,
    val peso: Float,
    val dataCadastro: Long = System.currentTimeMillis()
)

// Entidades simplificadas para Tratamento e Saúde (estrutura base)
@Entity(tableName = "tratamentos", foreignKeys = [ForeignKey(entity = Gato::class, parentColumns = ["id"], childColumns = ["gatoId"], onDelete = ForeignKey.CASCADE)])
data class Tratamento(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gatoId: Long,
    val tipo: String,
    val descricao: String,
    val dataAgendada: Long,
    val status: String = "PENDENTE"
)

@Entity(tableName = "registros_saude", foreignKeys = [ForeignKey(entity = Gato::class, parentColumns = ["id"], childColumns = ["gatoId"], onDelete = ForeignKey.CASCADE)])
data class RegistroSaude(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gatoId: Long,
    val tipo: String,
    val descricao: String,
    val data: Long = System.currentTimeMillis()
)