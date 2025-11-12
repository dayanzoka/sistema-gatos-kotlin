package com.example.sistema_cuidadogatos.repository

import com.example.sistema_cuidadogatos.api.JsonPlaceholderService
import com.example.sistema_cuidadogatos.api.PostApiModel
import com.example.sistema_cuidadogatos.database.dao.GatoDao
import com.example.sistema_cuidadogatos.database.dao.RegistroSaudeDao
import com.example.sistema_cuidadogatos.database.dao.TratamentoDao
import com.example.sistema_cuidadogatos.database.dao.TutorDao
import com.example.sistema_cuidadogatos.database.entities.GatoEntity
import com.example.sistema_cuidadogatos.database.entities.RegistroSaudeEntity
import com.example.sistema_cuidadogatos.database.entities.TratamentoEntity
import com.example.sistema_cuidadogatos.database.entities.TutorEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CuiGatoRepository(
    private val tutorDao: TutorDao,
    private val gatoDao: GatoDao,
    private val tratamentoDao: TratamentoDao,
    private val registroSaudeDao: RegistroSaudeDao,
    private val apiService: JsonPlaceholderService
) {
    // --- Tutores (Room) ---
    fun getAllTutors(): Flow<List<TutorEntity>> = tutorDao.getAllTutors()
    suspend fun getTutorById(id: Long) = tutorDao.getTutorById(id)
    suspend fun saveTutor(tutor: TutorEntity) = tutorDao.insert(tutor)
    suspend fun deleteTutor(tutor: TutorEntity) = tutorDao.delete(tutor)

    // --- Gatos (Room) ---
    fun getAllGatos(): Flow<List<GatoEntity>> = gatoDao.getAllGatos()
    fun getGatosByTutor(tutorId: Long): Flow<List<GatoEntity>> = gatoDao.getGatosByTutor(tutorId)
    suspend fun getGatoById(id: Long) = gatoDao.getGatoById(id)
    suspend fun saveGato(gato: GatoEntity) = gatoDao.insert(gato)
    suspend fun deleteGato(gato: GatoEntity) = gatoDao.delete(gato)

    // --- Tratamentos (Room) ---
    fun getAllTratamentos(): Flow<List<TratamentoEntity>> = tratamentoDao.getAllTratamentos()
    fun getTratamentosByGato(gatoId: Long): Flow<List<TratamentoEntity>> = tratamentoDao.getTratamentosByGato(gatoId)
    suspend fun saveTratamento(tratamento: TratamentoEntity) = tratamentoDao.insert(tratamento)
    suspend fun deleteTratamento(tratamento: TratamentoEntity) = tratamentoDao.delete(tratamento)

    // --- Registros de Saúde (Room) ---
    fun getRegistrosByGato(gatoId: Long): Flow<List<RegistroSaudeEntity>> = registroSaudeDao.getRegistrosByGato(gatoId)
    suspend fun saveRegistroSaude(registro: RegistroSaudeEntity) = registroSaudeDao.insert(registro)
    suspend fun deleteRegistroSaude(registro: RegistroSaudeEntity) = registroSaudeDao.delete(registro)

    // --- API Demo (Retrofit) ---
    suspend fun fetchUsersDemo() = apiService.getUsers()
    suspend fun createPostDemo(post: PostApiModel): Response<PostApiModel> = apiService.createPost(post)
    suspend fun updatePostDemo(id: Int, post: PostApiModel): Response<PostApiModel> = apiService.updatePost(id, post)
    suspend fun deletePostDemo(id: Int): Response<Unit> = apiService.deletePost(id)
}