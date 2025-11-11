package com.example.sistema_gatos.repository

import com.example.sistema_gatos.data.local.*
import com.example.sistema_gatos.data.remote.ApiService
import com.example.sistema_gatos.data.remote.PostDTO
import kotlinx.coroutines.flow.Flow

class AppRepository(private val dao: AppDao, private val api: ApiService) {

    // Local (Room)
    val todosTutores: Flow<List<Tutor>> = dao.getTodosTutores()

    suspend fun insertTutor(tutor: Tutor) = dao.insertTutor(tutor)
    suspend fun deleteTutor(tutor: Tutor) = dao.deleteTutor(tutor)

    fun getGatosPorTutor(tutorId: Long): Flow<List<Gato>> = dao.getGatosPorTutor(tutorId)
    suspend fun insertGato(gato: Gato) = dao.insertGato(gato)
    suspend fun deleteGato(gato: Gato) = dao.deleteGato(gato)

    // Remoto (Retrofit)
    suspend fun fetchApiUsers() = api.getUsers()
    suspend fun fetchApiPosts() = api.getPosts()
    suspend fun createApiPost(post: PostDTO) = api.createPost(post)
}