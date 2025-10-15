package com.mxlkt.mycities.pages.places

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mxlkt.mycities.R
import com.mxlkt.mycities.config.buildAuthorizedImageRequest
import com.mxlkt.mycities.data.model.Place
import com.mxlkt.mycities.data.view.PlaceDetailUiState
import com.mxlkt.mycities.data.view.PlaceDetailViewModel
import com.mxlkt.mycities.util.HtmlText

@Composable
fun PlaceView(
    placeId: String?,
    navController: NavController,
    viewModel: PlaceDetailViewModel = viewModel()
) {
    LaunchedEffect(key1 = placeId) {
        // PERBAIKAN: Ubah String ke Int sebelum dikirim ke ViewModel
        placeId?.toIntOrNull()?.let { id ->
            viewModel.getPlaceById(id)
        }
    }

    val uiState = viewModel.placeDetailUiState

    // 2. Menampilkan UI berdasarkan state (Loading, Success, Error)
    when (uiState) {
        is PlaceDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is PlaceDetailUiState.Success -> {
            // Jika data berhasil didapat, tampilkan kontennya
            PlaceDetailContent(place = uiState.place, navController = navController)
        }
        is PlaceDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Gagal memuat data detail tempat.")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailContent(place: Place, navController: NavController) {
    val context = LocalContext.current

    // 2. Panggil fungsi helper untuk membuat request gambar yang aman
    val imageRequest = buildAuthorizedImageRequest(context, place.images)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                // 3. Judul dinamis dari data API
                title = { Text(text = place.name) },
                navigationIcon = {
                    // 4. Navigasi kembali yang benar
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF172F60),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = imageRequest ?: R.drawable.not_found,
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.not_found),
                error = painterResource(id = R.drawable.not_found),
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            // Kurung kurawal yang salah sebelumnya ada di sini, sekarang sudah dihapus.

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = place.name,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            HtmlText(
                html = place.descriptions ?: "Deskripsi tidak tersedia.",
                style = TextStyle(
                    color = Color(0xFF262626),
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

        } // <-- PERBAIKAN: Kurung kurawal penutup Column sekarang ada di posisi yang benar.
    }
}