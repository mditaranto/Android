package com.example.nombrecito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.nombrecito.Room.CrearDatabase
import com.example.nombrecito.ui.theme.NombrecitoTheme

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var database: CrearDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        database = Room.databaseBuilder(this, CrearDatabase::class.java, "bbdd").build()

        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            NombrecitoTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController,
                    startDestination = "VistaEjemplo") {

                    composable("VistaEjemplo" )  {
                        VistaEjemplo(navController)
                    }

                    composable("puta/{numero}", arguments = listOf(navArgument("numero"){
                        type = NavType.StringType})) {
                        VistaEjemplo2(navController, it.arguments?.getString("numero") ?: "2") }

                    }
                }
            }
        }
    }

