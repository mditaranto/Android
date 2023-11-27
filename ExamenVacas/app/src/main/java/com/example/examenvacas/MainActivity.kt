package com.example.examenvacas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.examenvacas.Entidades.tVacaEntity
import com.example.examenvacas.ui.theme.ExamenVacasTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity() : ComponentActivity() {

    companion object {
        lateinit var database2: VacasDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database2 = Room.databaseBuilder(this, VacasDatabase::class.java, "VacasBBDD").build()

        setContent {
            ExamenVacasTheme {
                val navController = rememberNavController()

                //Empezara en la vista Login
                NavHost(
                    navController = navController,
                    startDestination = "Configuracion"
                ) {
                    composable(route = "Vacas") { Vacas(navController) }
                    composable(route = "Configuracion") { Configuracion(navController) }
                }

            }

        }
    }

}