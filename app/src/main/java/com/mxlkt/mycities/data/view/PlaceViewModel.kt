package com.mxlkt.mycities.data.view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxlkt.mycities.config.ApiConf
import com.mxlkt.mycities.data.model.Place
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PlaceUiState {
    data class Success(val places: List<Place>) : PlaceUiState
    object Error : PlaceUiState
    object Loading : PlaceUiState
}

class PlaceViewModel : ViewModel() {
    var placeUiState: PlaceUiState by mutableStateOf(PlaceUiState.Loading)
        private set

    fun getPlacesByCategoryId(categoryId: String) {
        // LOG 1: Pastikan fungsi ini dipanggil dengan ID yang benar
        Log.d("PlaceViewModel", "Fetching places for category ID: $categoryId")

        viewModelScope.launch {
            placeUiState = PlaceUiState.Loading
            placeUiState = try {
                val result = ApiConf.apiService.getPlacesByCategoryId(categoryId)
                // LOG 2: Cek apakah kita mendapat hasil dan berapa jumlahnya
                Log.d("PlaceViewModel", "Success! Received ${result.size} places.")
                PlaceUiState.Success(result)
            } catch (e: Exception) { // Gunakan Exception umum untuk menangkap semua error
                // LOG 3: Jika ada error, cetak pesan errornya
                Log.e("PlaceViewModel", "Error fetching places: ${e.message}")
                PlaceUiState.Error
            }
        }
    }
}
