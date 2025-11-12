package com.example.sistema_cuidadogatos.database.dao

import androidx.room.*
import com.example.sistema_cuidadogatos.database.entities.TratamentoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TratamentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tratamento: TratamentoEntity): Long

    @Update
    suspend fun update(tratamento: TratamentoEntity)

    @Delete
    suspend fun delete(tratamento: TratamentoEntity)

    @Query("SELECT * FROM tratamento ORDER BY dataAgendada DESC")
    fun getAllTratamentos(): Flow<List<TratamentoEntity>>

    @Query("SELECT * FROM tratamento WHERE gatoId = :gatoId ORDER BY dataAgendada DESC")
    fun getTratamentosByGato(gatoId: Long): Flow<List<TratamentoEntity>>
}