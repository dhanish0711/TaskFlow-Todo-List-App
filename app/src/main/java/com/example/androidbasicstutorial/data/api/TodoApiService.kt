package com.example.androidbasicstutorial.data.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Serializable
data class ApiTodo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<ApiTodo>

    @POST("todos")
    suspend fun createTodo(@Body todo: ApiTodo): ApiTodo
}

object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TodoApiService::class.java)
    }
}
