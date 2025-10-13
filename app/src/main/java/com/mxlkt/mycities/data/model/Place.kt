package com.mxlkt.mycities.data.model
import com.google.gson.annotations.SerializedName

data class Place(
    val id: Int,
    val name: String,
    @SerializedName("idCategory") // Sesuaikan jika nama variabel berbeda dari JSON
    val idCategory: Int,
    val descriptions: String,
    val images: String
)
