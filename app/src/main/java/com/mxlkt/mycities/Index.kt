package com.mxlkt.mycities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mxlkt.mycities.config.Routes

@Composable
fun Index(navController: NavController)
{
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White).
            padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item {
//            Image(
//                painter = painterResource(id = R.drawable.icon_realarm),
//                contentDescription = "imageLogin",
//                modifier = Modifier.size(200.dp)
//            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "SamarindaKu",
                fontSize = 20.sp,
                color = Color.Black, // agar teks terlihat di background hitamm
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "mari kita jelajahi kota Samarinda",
                fontSize = 14.sp,
                color = Color.Black, // agar teks terlihat di background hitam
            )

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth()
                    .height(60.dp),
                onClick = {
                    navController.navigate(Routes.Dashboard)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0D2764),    // warna background
                    contentColor = Color.White        // warna teks/icon
                ),

                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Jelajahi!", fontSize = 20.sp)
            }





        }
    }
}