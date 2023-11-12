package com.example.agendaconbbd

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendaconbbd.Entity.ContactosEntity
import com.example.agendaconbbd.ui.theme.AgendaConBBDTheme

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
                        editarContacto(navController, it.arguments?.getLong("id") ?: 1)
                    }
                }
            }
        }
    }
}

