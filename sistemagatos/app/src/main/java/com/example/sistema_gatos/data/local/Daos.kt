package com.example.sistema_gatos.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // Tutores
    @Query("SELECT * FROM tutores ORDER BY nome ASC")
    fun getTodosTutores(): Flow<List<Tutor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTutor(tutor: Tutor)

    @Delete
    suspend fun deleteTutor(tutor: Tutor)


    // Gatos
    @Query("SELECT * FROM gatos WHERE tutorId = :tutorId ORDER BY nome ASC")
    fun getGatosPorTutor(tutorId: Long): Flow<List<Gato>>

    @Query("SELECT * FROM gatos ORDER BY nome ASC")
    fun getTodosGatos(): Flow<List<Gato>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGato(gato: Gato)

    @Delete
    suspend fun deleteGato(gato: Gato)


    // Tratamentos
    @Query("SELECT * FROM tratamentos WHERE gatoId = :gatoId ORDER BY dataAgendada DESC")
    fun getTratamentosPorGato(gatoId: Long): Flow<List<Tratamento>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTratamento(tratamento: Tratamento)

    @Delete
    suspend fun deleteTratamento(tratamento: Tratamento)

    @Update
    suspend fun updateTratamento(tratamento: Tratamento)


    // Registros de Saúde
    @Query("SELECT * FROM registros_saude WHERE gatoId = :gatoId ORDER BY data DESC")
    fun getRegistrosSaudePorGato(gatoId: Long): Flow<List<RegistroSaude>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistroSaude(registro: RegistroSaude)

    @Delete
    suspend fun deleteRegistroSaude(registro: RegistroSaude)

    @Update
    suspend fun updateRegistroSaude(registro: RegistroSaude)
}