package com.example.sistema_cuidadogatos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sistema_cuidadogatos.database.dao.GatoDao
import com.example.sistema_cuidadogatos.database.dao.RegistroSaudeDao
import com.example.sistema_cuidadogatos.database.dao.TratamentoDao
import com.example.sistema_cuidadogatos.database.dao.TutorDao
import com.example.sistema_cuidadogatos.database.entities.GatoEntity
import com.example.sistema_cuidadogatos.database.entities.RegistroSaudeEntity
import com.example.sistema_cuidadogatos.database.entities.TratamentoEntity
import com.example.sistema_cuidadogatos.database.entities.TutorEntity

@Database(
    entities = [
        TutorEntity::class,
        GatoEntity::class,
        TratamentoEntity::class,
        RegistroSaudeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CuiGatoDatabase : RoomDatabase() {
    abstract fun tutorDao(): TutorDao
    abstract fun gatoDao(): GatoDao
    abstract fun tratamentoDao(): TratamentoDao
    abstract fun registroSaudeDao(): RegistroSaudeDao
}