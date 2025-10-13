package com.mxlkt.mycities.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mxlkt.mycities.R
import com.mxlkt.mycities.config.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboards(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Kategori - My City") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(modifier = Modifier.padding(12.dp)) {
                Image(
                        painter = painterResource(R.drawable.mahakam_bridge),
                        contentDescription = null,
                        modifier = Modifier.height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.height(100.dp) // biar tinggi kolomnya jelas
                ) {
                    Text(
                        "Wisata",
                        style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        "lorem ipsum dolor sit amet",
                        style = TextStyle(color = Color(0xFF2F2F2F), fontSize = 11.sp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            navController.navigate(Routes.PlaceList)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0D2764), // warna latar tombol
                            contentColor = Color.White          // warna teks
                        ),
                        modifier = Modifier
                            .height(25.dp)
                    ) {
                        Text("lihat selengkapnya..", style = TextStyle(fontSize = 11.sp))
                    }
                }


            }


        }


    }

}