package com.example.agendaconbbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var database: ContactosDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(this, ContactosDatabase::class.java, "Contactos-db").build()

        setContent {
            val navController = rememberNavController()
            Column(

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Empezara en la vista Login
                NavHost(
                    navController = navController,
                    startDestination = "ListaContactos"
                ) {
                    //Vista Login
                    composable(route = "ListaContactos") { ListaContactos(navController) }
                    composable(route = "AñadirContacto") { AñadirContacto(navController) }
                    composable(route = "editarContacto/{id}", arguments = listOf(navArgument("id") {
                        type = NavType.LongType
                    })) {
                        EditarContacto(navController, it.arguments?.getLong("id") ?: 1)
                    }
                }
            }
        }
    }
}

