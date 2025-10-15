package com.mxlkt.mycities.config

import android.content.Context
import coil.request.ImageRequest
import okhttp3.Headers

/**
 * Object untuk menyimpan konfigurasi konstan terkait API media.
 * Lebih rapi untuk menyimpan URL dasar dan kunci API di satu tempat.
 */
object MediaApiConfig {
    // GANTI DENGAN API KEY ANDA YANG SEBENARNYA
    const val API_KEY = "iawcjiawwed@dofash43hxf"
    const val BASE_URL = "https://mycities.b4its.tech/api/media/"
}

/**
 * Fungsi helper untuk membangun ImageRequest yang sudah terotorisasi.
 *
 * @param context Context yang diperlukan oleh Coil.
 * @param imagePath Path relatif gambar (contoh: "place/gambar.jpg").
 * @return Sebuah objek ImageRequest jika imagePath valid, atau null jika tidak.
 */
fun buildAuthorizedImageRequest(context: Context, imagePath: String?): ImageRequest? {
    // Jika path gambar null atau kosong, kita tidak bisa membuat request, jadi kembalikan null.
    if (imagePath.isNullOrEmpty()) {
        return null
    }

    // Gabungkan URL dasar dari config dengan path gambar.
    val fullImageUrl = "${MediaApiConfig.BASE_URL}$imagePath"

    // Bangun dan kembalikan ImageRequest yang sudah lengkap dengan header.
    return ImageRequest.Builder(context)
        .data(fullImageUrl)
        .headers(
            Headers.Builder()
                .add("Authorization", "Bearer ${MediaApiConfig.API_KEY}")
                .build()
        )
//    Ini adalah cara yang lebih eksplisit dan kompatibel untuk membuat dan menambahkan header ke permintaan gambar Anda di Coil.
        .crossfade(true) // Menambahkan efek transisi halus
        .build()
}
