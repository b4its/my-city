package com.mxlkt.mycities.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.mxlkt.mycities.R
import com.mxlkt.mycities.config.Routes
import com.mxlkt.mycities.data.model.Category
import com.mxlkt.mycities.data.view.CategoryUiState
import com.mxlkt.mycities.data.view.DashboardViewModel

import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboards(
    navController: NavController,
    dashboardViewModel: DashboardViewModel = viewModel() // Inject ViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Kategori - SamarindaKu") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF0D2764),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        // Ambil state dari ViewModel
        val uiState = dashboardViewModel.categoryUiState

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (uiState) {
                is CategoryUiState.Loading -> {
                    // Tampilkan loading indicator di tengah layar
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CategoryUiState.Success -> {
                    // Jika sukses, tampilkan daftar kategori
                    CategoryList(categories = uiState.categories, navController = navController)
                }
                is CategoryUiState.Error -> {
                    // Jika error, tampilkan pesan kesalahan
                    Text("Gagal memuat data...", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun CategoryList(categories: List<Category>, navController: NavController) {
    // Gunakan LazyColumn untuk daftar yang efisien
    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category, navController = navController)
        }
    }
}

@Composable
fun CategoryItem(category: Category, navController: NavController) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        // Menambahkan onClick pada Card agar seluruh area bisa diklik
        onClick = { navController.navigate("${Routes.PlaceList}/${category.id}") }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Membuat item di Row menjadi rata tengah
        ) {
            // PERBAIKAN 1 & 2: Kondisi aman dan modifier konsisten
            // Menggunakan satu AsyncImage saja sudah cukup untuk handle semua kondisi.
            AsyncImage(
                model = if (!category.images.isNullOrEmpty()) {
                    "http://192.168.1.4:8000/${category.images}"
                } else {
                    R.drawable.mahakam_bridge // Tampilkan resource jika URL kosong/null
                },
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                // Placeholder akan muncul saat gambar dari URL sedang dimuat
                placeholder = painterResource(id = R.drawable.mahakam_bridge),
                modifier = Modifier
                    .size(100.dp) // Modifier yang konsisten
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Menggunakan Box agar lebih fleksibel menempatkan tombol di bawah
            Box(
                modifier = Modifier
                    .height(100.dp) // Samakan tinggi dengan gambar
                    .weight(1f) // Mengisi sisa ruang agar tombol bisa didorong ke bawah
            ) {
                Column {
                    // SARAN: Gunakan style dari MaterialTheme
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium, // Contoh: Title Medium
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Temukan ${category.name.lowercase()} terbaik di kota ini.",
                        style = MaterialTheme.typography.bodySmall, // Contoh: Body Small
                        color = Color.Gray // Gunakan warna yang lebih lembut
                    )
                }

                // Tombol ini akan terdorong ke pojok kanan bawah Box
                Button(
                    onClick = {
                        navController.navigate("${Routes.PlaceList}/${category.id}")
                    },
                    // SARAN: Gunakan warna dari MaterialTheme
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D2764),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .height(30.dp)
                        .align(Alignment.BottomEnd), // Dorong tombol ke bawah kanan
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text("Lihat Selengkapnya", fontSize = 11.sp)
                }
            }
        }
    }
}