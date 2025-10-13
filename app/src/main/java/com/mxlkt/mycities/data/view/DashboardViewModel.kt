package com.mxlkt.mycities.data.view


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxlkt.mycities.data.model.Category
import com.mxlkt.mycities.config.ApiConf
import kotlinx.coroutines.launch
import java.io.IOException

// Definisikan state untuk UI
sealed interface CategoryUiState {
    data class Success(val categories: List<Category>) : CategoryUiState
    object Error : CategoryUiState
    object Loading : CategoryUiState
}

class DashboardViewModel : ViewModel() {

    // State yang akan diobservasi oleh UI
    var categoryUiState: CategoryUiState by mutableStateOf(CategoryUiState.Loading)
        private set

    init {
        // Panggil API saat ViewModel pertama kali dibuat
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryUiState = CategoryUiState.Loading
            categoryUiState = try {
                val result = ApiConf.apiService.getCategories()
                CategoryUiState.Success(result)
            } catch (e: IOException) {
                // Tangani error koneksi
                CategoryUiState.Error
            } catch (e: retrofit2.HttpException) {
                // Tangani error HTTP (404, 500, dll)
                CategoryUiState.Error
            }
        }
    }
}