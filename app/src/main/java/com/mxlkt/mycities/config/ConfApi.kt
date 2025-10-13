package com.mxlkt.mycities.config

import com.mxlkt.mycities.data.model.Category
import com.mxlkt.mycities.data.model.Place
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// --- PERBAIKAN DI SINI ---
interface ApiService {
    // Path sudah lengkap, tidak perlu @Header lagi
    @GET("api/categories")
    suspend fun getCategories(): List<Category>

    // Path sudah lengkap, tidak perlu @Header lagi
    @GET("api/places")
    suspend fun getPlaces(): List<Place>

    // Path sudah lengkap, tidak perlu @Header lagi
    @GET("api/categories/{id}")
    suspend fun getCategoryById(@Path("id") categoryId: Int): Category

    // FUNGSI BARU: Untuk mengambil daftar tempat berdasarkan ID kategori
    @GET("api/places/categories/{id}")
    suspend fun getPlacesByCategoryId(@Path("id") categoryId: String): List<Place>

    // ▼▼▼ TAMBAHKAN FUNGSI INI ▼▼▼
    @GET("api/places/{id}")
    suspend fun getPlaceById(@Path("id") placeId: String): Place // Asumsi responsnya adalah satu objek Place
}

// Tidak ada perubahan di object ApiConf, sudah bagus
object ApiConf {
    private const val BASE_URL = "http://192.168.1.4:8000/"
    // Ganti token ini jika diperlukan
    private const val AUTH_TOKEN = "iawcjiawwed@dofash43"

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", "Bearer $AUTH_TOKEN")
            .build()
        chain.proceed(requestHeaders)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}