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

    //Creacion de la variable de UsuariosDatabase
    companion object {
        lateinit var database: UsuariosDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Asignamos el valor a la base de datos creandola con nombre "usuarios-db"
        database =
            Room.databaseBuilder(this, UsuariosDatabase::class.java, "usuarios-db").build()

        //Hacemos el navController
        setContent {
            val navController = rememberNavController()
            Column(

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Empezara en la vista Login
                NavHost(
                    navController = navController,
                    startDestination = "Login"
                ) {
                    //Vista Login
                    composable(route="Login") { Login(navController) }
                    //Esta vista tiene un parametro de entrada desde otra vista ("user")
                    composable(route="Juego/{User}", arguments = listOf(navArgument("User") {
                        type = NavType.StringType
                    })) {
                        //El argumento que recibe es "User" y si no se asigna el valor "Invitado"
                        Juego(navController, it.arguments?.getString("User") ?: "Invitado")
                    }
                    //Vista Estadisticas
                    composable(route="Estadisticas") { Estadisticas(navController)}

                }
            }

        }
    }
}

