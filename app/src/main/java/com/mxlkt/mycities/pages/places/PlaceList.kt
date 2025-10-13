package com.mxlkt.mycities.pages.places

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mxlkt.mycities.R
import com.mxlkt.mycities.config.Routes
import com.mxlkt.mycities.data.model.Place
import com.mxlkt.mycities.data.view.PlaceUiState
import com.mxlkt.mycities.data.view.PlaceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceList(
    // Terima categoryId dari Navigasi dan inject ViewModel
    categoryId: String?,
    navController: NavController,
    placeViewModel: PlaceViewModel = viewModel()
) {
    // Minta data ke ViewModel saat layar pertama kali ditampilkan
    LaunchedEffect(key1 = categoryId) {
        if (categoryId != null) {
            placeViewModel.getPlacesByCategoryId(categoryId)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Daftar Tempat - SamarindaKu") },
                navigationIcon = {
                    // PERBAIKAN: Gunakan popBackStack untuk tombol kembali
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF172F60),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
    ) { paddingValues ->
        val uiState = placeViewModel.placeUiState

        // Tampilkan UI berdasarkan state dari ViewModel
        Box(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            when (uiState) {
                is PlaceUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is PlaceUiState.Success -> {
                    // Jika data berhasil didapat, tampilkan daftar tempat
                    LazyColumn(
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.places) { place ->
                            PlaceItem(place = place, navController = navController)
                        }
                    }
                }
                is PlaceUiState.Error -> {
                    Text("Gagal memuat data.", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
@Composable
fun PlaceItem(place: Place, navController: NavController) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(),
        // PENINGKATAN UX: Membuat seluruh kartu bisa diklik agar lebih mudah
        onClick = { navController.navigate("${Routes.PlaceView}/${place.id}") }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically // Membuat konten rata tengah vertikal
        ) {
            // PERBAIKAN 1 & 2: Menggunakan satu AsyncImage dengan modifier konsisten
            AsyncImage(
                model = if (!place.images.isNullOrEmpty()) {
                    "http://192.168.1.4:8000/${place.images}"
                } else {
                    R.drawable.not_found // Fallback jika URL null atau kosong
                },
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.not_found),
                error = painterResource(id = R.drawable.not_found),
                modifier = Modifier
                    .size(100.dp) // <-- Modifier yang konsisten
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp)) // Sedikit lebih lebar

            Column(
                // Menghapus modifier height agar tinggi kolom fleksibel
                // dan mengatur agar kolom mengisi sisa ruang
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = place.name,
                    // SARAN: Gunakan style dari MaterialTheme
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Alamat tidak tersedia.", // Gunakan data asli jika ada
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2
                )
                // Spacer dengan weight tidak diperlukan lagi jika tombol diletakkan di sini
                Spacer(modifier = Modifier.height(16.dp))

                // Tombol diletakkan di pojok kanan
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            navController.navigate("${Routes.PlaceView}/${place.id}")
                        },
                        // SARAN: Gunakan warna dari MaterialTheme
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0D2764),
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .height(30.dp)
                            .align(Alignment.CenterEnd), // Posisi di ujung kanan
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text("Lihat selengkapnya..", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}