package com.mxlkt.mycities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mxlkt.mycities.config.Routes
import com.mxlkt.mycities.config.Routes.Dashboard
import com.mxlkt.mycities.pages.Dashboards
import com.mxlkt.mycities.pages.places.PlaceList
import com.mxlkt.mycities.pages.places.PlaceView
import com.mxlkt.mycities.ui.theme.MyCitiesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                    navController.navigate(Routes.MainPages)
            }

            // routing
            NavHost(
                navController = navController,
                startDestination = Routes.MainPages
            ) {
                composable(Routes.MainPages) {
                    Index(navController)
                }
                composable(Routes.Dashboard) {
                    Dashboards(navController)
                }
                composable(
                    route = "${Routes.PlaceList}/{idCategory}",
                    arguments = listOf(navArgument("idCategory") {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getString("idCategory")
                    PlaceList(categoryId = categoryId, navController = navController)
                }

                // --- PERBAIKAN DI SINI ---
                // Siapkan route PlaceView untuk menerima argumen 'placeId'
                composable(
                    route = "${Routes.PlaceView}/{placeId}",
                    arguments = listOf(navArgument("placeId") {
                        type = NavType.StringType // Asumsi ID tempat adalah String atau Int
                    })
                ) { backStackEntry ->
                    val placeId = backStackEntry.arguments?.getString("placeId")

                    // Panggil Composable PlaceView Anda dengan ID yang didapat
                    PlaceView(placeId = placeId, navController = navController)
                }
            

            }


        }
    }
}
