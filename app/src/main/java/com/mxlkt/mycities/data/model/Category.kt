package com.mxlkt.mycities.data.model
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val images: String? // Bisa null sesuai JSON
)

