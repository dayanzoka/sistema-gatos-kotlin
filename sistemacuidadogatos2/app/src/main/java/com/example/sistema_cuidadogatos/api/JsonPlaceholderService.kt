package com.example.sistema_cuidadogatos.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

// Modelo de Dados para POSTS (demonstração)
data class PostApiModel(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)

// Modelo de Dados para Usuários (demonstração)
data class UserApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
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