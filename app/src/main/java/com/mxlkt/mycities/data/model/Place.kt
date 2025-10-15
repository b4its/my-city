package com.mxlkt.mycities.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: Int,
    val name: String,
    val address: String? = null,

    @SerialName("idCategory")
    val idCategory: Int,

    // PERBAIKAN: Beri nilai default null agar field ini menjadi opsional
    val descriptions: String? = null,

    // PERBAIKAN: Lakukan hal yang sama untuk images agar lebih aman
    val images: String? = null
)