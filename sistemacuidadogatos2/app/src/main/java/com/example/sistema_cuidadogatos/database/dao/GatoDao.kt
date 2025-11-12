package com.example.sistema_cuidadogatos.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy
import com.example.sistema_cuidadogatos.database.entities.GatoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GatoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gato: GatoEntity): Long

    @Update
    suspend fun update(gato: GatoEntity)

    @Delete
    suspend fun delete(gato: GatoEntity)

    @Query("SELECT * FROM gato ORDER BY nome ASC")
    fun getAllGatos(): Flow<List<GatoEntity>>

    @Query("SELECT * FROM gato WHERE tutorId = :tutorId ORDER BY nome ASC")
    fun getGatosByTutor(tutorId: Long): Flow<List<GatoEntity>>

    @Query("SELECT * FROM gato WHERE id = :id")
    suspend fun getGatoById(id: Long): GatoEntity?
}