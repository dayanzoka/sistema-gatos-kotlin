package com.example.sistema_cuidadogatos.database.dao

import androidx.room.*
import com.example.sistema_cuidadogatos.database.entities.TutorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TutorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tutor: TutorEntity): Long

    @Update
    suspend fun update(tutor: TutorEntity)

    @Delete
    suspend fun delete(tutor: TutorEntity)

    @Query("SELECT * FROM tutor ORDER BY nome ASC")
    fun getAllTutors(): Flow<List<TutorEntity>>

    @Query("SELECT * FROM tutor WHERE id = :id")
    suspend fun getTutorById(id: Long): TutorEntity?
}