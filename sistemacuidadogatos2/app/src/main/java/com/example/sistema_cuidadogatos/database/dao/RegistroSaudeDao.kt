package com.example.sistema_cuidadogatos.database.dao

import androidx.room.*
import com.example.sistema_cuidadogatos.database.entities.RegistroSaudeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroSaudeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registro: RegistroSaudeEntity): Long

    @Update
    suspend fun update(registro: RegistroSaudeEntity)

    @Delete
    suspend fun delete(registro: RegistroSaudeEntity)

    @Query("SELECT * FROM registro_saude WHERE gatoId = :gatoId ORDER BY data DESC")
    fun getRegistrosByGato(gatoId: Long): Flow<List<RegistroSaudeEntity>>
}