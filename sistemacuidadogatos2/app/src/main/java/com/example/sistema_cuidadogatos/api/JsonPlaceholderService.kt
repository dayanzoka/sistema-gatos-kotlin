package com.example.sistema_cuidadogatos.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

// Modelo de Dados para POSTS (demonstração)
@Serializable // ✅ Adicionado: Essencial para o Kotlin Serialization funcionar
data class PostApiModel(
    @SerialName("userId") val userId: Int, // ✅ Alterado: @SerializedName (GSON) -> @SerialName (Kotlinx)
    @SerialName("id") val id: Int?,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String
)

// Modelo de Dados para Usuários (demonstração)
@Serializable // ✅ Adicionado
data class UserApiModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String
)

interface JsonPlaceholderService {
    // GET: Buscar lista de usuários
    @GET("/users")
    suspend fun getUsers(): Response<List<UserApiModel>>

    // GET: Buscar lista de posts
    @GET("/posts")
    suspend fun getPosts(): Response<List<PostApiModel>>

    // POST: Criar Post
    @POST("/posts")
    suspend fun createPost(@Body post: PostApiModel): Response<PostApiModel>

    // PUT: Atualizar Post
    @PUT("/posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostApiModel): Response<PostApiModel>

    // DELETE: Deletar Post
    @DELETE("/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}