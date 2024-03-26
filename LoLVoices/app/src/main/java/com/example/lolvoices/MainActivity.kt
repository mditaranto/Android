package com.example.lolvoices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lolvoices.Vistas.CampeonScreen
import com.example.lolvoices.Vistas.CampeonesScreen
import com.example.lolvoices.Vistas.FavoritosScreen
import com.example.lolvoices.Vistas.JueguitoScreen
import com.example.lolvoices.ui.theme.LoLVoicesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            LoLVoicesTheme {
                // A surface container using the 'background' color from the theme
                NavHost (navController = navController, startDestination = "CampeonesScreen") {
                    composable("CampeonesScreen") {
                        CampeonesScreen(navController)
                    }
                    composable( "CampeonScreen/{nombre}") {
                        CampeonScreen(navController, it.arguments?.getString("nombre") ?: "Aatrox")
                    }
                    composable( "JueguitoScreen") {
                        JueguitoScreen(navController)
                    }
                    composable( "FavoritosScreen") {
                        FavoritosScreen(navController)
                    }

                }
            }
        }
    }
}