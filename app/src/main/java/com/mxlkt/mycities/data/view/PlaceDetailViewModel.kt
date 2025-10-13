// File baru: com/mxlkt/mycities/data/view/PlaceDetailViewModel.kt

package com.mxlkt.mycities.data.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxlkt.mycities.config.ApiConf
import com.mxlkt.mycities.data.model.Place
import kotlinx.coroutines.launch
import java.io.IOException

// Definisikan state untuk UI detail tempat
sealed interface PlaceDetailUiState {
    data class Success(val place: Place) : PlaceDetailUiState // Hanya satu 'Place'
    object Error : PlaceDetailUiState
    object Loading : PlaceDetailUiState
}

class PlaceDetailViewModel : ViewModel() {

    var placeDetailUiState: PlaceDetailUiState by mutableStateOf(PlaceDetailUiState.Loading)
        private set

    // Fungsi untuk mengambil detail tempat berdasarkan ID-nya
    fun getPlaceById(placeId: String) {
        viewModelScope.launch {
            placeDetailUiState = PlaceDetailUiState.Loading
            placeDetailUiState = try {
                val result = ApiConf.apiService.getPlaceById(placeId)
                PlaceDetailUiState.Success(result)
            } catch (e: IOException) {
                PlaceDetailUiState.Error
            } catch (e: retrofit2.HttpException) {
                PlaceDetailUiState.Error
            }
        }
    }
}