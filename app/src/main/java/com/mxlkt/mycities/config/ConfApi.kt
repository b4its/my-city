package com.mxlkt.mycities.config

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory // <-- TAMBAHKAN BARIS INI
import com.mxlkt.mycities.data.model.Category
import com.mxlkt.mycities.data.model.Place
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.logging.HttpLoggingInterceptor // <-- TAMBAHKAN IMPORT INI

interface ApiService {
    @GET("api/categories")
    suspend fun getCategories(): List<Category>

    @GET("api/places")
    suspend fun getPlaces(): List<Place>

    @GET("api/categories/{id}")
    suspend fun getCategoryById(@Path("id") categoryId: Int): Category // <-- Ubah ke Int

    @GET("api/places/categories/{id}")
    suspend fun getPlacesByCategoryId(@Path("id") categoryId: Int): List<Place> // <-- Ubah ke Int

    @GET("api/places/{id}")
    suspend fun getPlaceById(@Path("id") placeId: Int): Place // <-- Ubah ke Int
}

object ApiConf {
    private const val BASE_URL = "https://mycities.b4its.tech/"
    private const val AUTH_TOKEN = "iawcjiawwed@dofash43hxf"

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", "Bearer $AUTH_TOKEN")
            .build()
        chain.proceed(requestHeaders)
    }

    // --- TAMBAHKAN BLOK INI ---
    // Interceptor untuk logging. Berguna untuk debugging.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Level BODY akan menampilkan semua detail
    }
    // -------------------------

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor) // <-- TAMBAHKAN LOGGING INTERCEPTOR DI SINI
        .build()

    private val json = Json { ignoreUnknownKeys = true }
    private val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType)) // Baris ini sekarang akan dikenali
        .client(client)
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}