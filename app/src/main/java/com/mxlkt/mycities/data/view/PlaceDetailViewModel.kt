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
 * Sealed interface untuk state UI di layar detail tempat.
 */
sealed interface PlaceDetailUiState {
    data class Success(val place: Place) : PlaceDetailUiState
    object Error : PlaceDetailUiState
    object Loading : PlaceDetailUiState
}

/**
 * ViewModel yang bertanggung jawab untuk mengambil data detail satu tempat.
 */
class PlaceDetailViewModel : ViewModel() {

    /**
     * Properti untuk menampung state UI detail.
     */
    var placeDetailUiState: PlaceDetailUiState by mutableStateOf(PlaceDetailUiState.Loading)
        private set

    /**
     * Fungsi untuk mengambil data satu tempat berdasarkan ID-nya.
     */
    fun getPlaceById(placeId: Int) {
        viewModelScope.launch {
            placeDetailUiState = PlaceDetailUiState.Loading
            placeDetailUiState = try {
                val result = ApiConf.apiService.getPlaceById(placeId)
                PlaceDetailUiState.Success(result)
            } catch (e: IOException) {
                PlaceDetailUiState.Error
            } catch (e: HttpException) {
                PlaceDetailUiState.Error
            }
        }
    }
}