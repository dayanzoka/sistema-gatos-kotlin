package com.example.sistema_gatos.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// Interface da API
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDTO>

    @GET("posts")
    suspend fun getPosts(): List<PostDTO>

    @POST("posts")
    suspend fun createPost(@Body post: PostDTO): PostDTO

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostDTO): PostDTO

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}

// Singleton do Retrofit
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}