package com.example.pptconbbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var database: UsuariosDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database =
            Room.databaseBuilder(this, UsuariosDatabase::class.java, "usuarios-db").build()

        setContent {
            val navController = rememberNavController()
            Column(

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "Login"
                ) {
                    composable(route="Login") { Login(navController) }
                    composable(route="Juego/{User}", arguments = listOf(navArgument("User") {
                        type = NavType.StringType
                    })) {
                        Juego(navController, it.arguments?.getString("User") ?: "Invitado")
                    }
                    composable(route="Estadisticas") { Estadisticas(navController)}

                }
            }

        }
    }
}

