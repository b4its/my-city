package com.mxlkt.mycities.data.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxlkt.mycities.config.ApiConf
import com.mxlkt.mycities.data.model.Place
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * Sealed interface untuk merepresentasikan semua kemungkinan state UI
 * untuk daftar tempat. Ini lebih aman dan jelas daripada menggunakan boolean.
 */
sealed interface PlaceUiState {
    data class Success(val places: List<Place>) : PlaceUiState
    object Error : PlaceUiState
    object Loading : PlaceUiState
}

/**
 * ViewModel yang bertanggung jawab untuk mengambil dan mengelola
 * data daftar tempat dari API.
 */
class PlaceViewModel : ViewModel() {

    /**
     * Properti untuk menampung state UI saat ini.
     * Composable akan "mendengarkan" perubahan pada properti ini.
     * Dibuat 'private set' agar hanya bisa diubah dari dalam ViewModel ini.
     */
    var placeUiState: PlaceUiState by mutableStateOf(PlaceUiState.Loading)
        private set

    /**
     * Fungsi untuk memulai proses pengambilan data dari API berdasarkan ID kategori.
     */
    fun getPlacesByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            // 1. Set state ke Loading sebelum memulai network call
            placeUiState = PlaceUiState.Loading

            // 2. Gunakan try-catch untuk menangani kemungkinan error
            placeUiState = try {
                // Panggil suspend function dari ApiService
                val result = ApiConf.apiService.getPlacesByCategoryId(categoryId)
                // Jika berhasil, update state ke Success dengan data yang didapat
                PlaceUiState.Success(result)
            } catch (e: IOException) {
                // Tangani error jaringan (misal: tidak ada internet)
                PlaceUiState.Error
            } catch (e: HttpException) {
                // Tangani error respons HTTP (misal: 404 Not Found, 500 Server Error)
                PlaceUiState.Error
            }
        }
    }
}