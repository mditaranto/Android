package com.example.nombrecito

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.room.Room
import com.example.nombrecito.Room.Entidades.Persona
import com.example.nombrecito.Room.getAllPersona
import com.example.nombrecito.Room.listaPersonas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun VistaEjemplo(navController: NavController) {

    val numeroSecreto = "Mellamomarcos"

    Column {
        Row {
            Text(text = "hola")

            Button(onClick = {navController.navigate("puta/${numeroSecreto}")}) {

            }
        }
        Row {
            Text(text = "Adios")

        }
    }
}

@Composable
fun VistaEjemplo2(navController: NavController, numero: String) {

    val lista by remember {
        mutableStateOf(mutableListOf<Persona>())
    }

    LaunchedEffect(Unit) {
        val personas = withContext(Dispatchers.IO) {
            //Get de los usuarios
            listaPersonas()
        }

        lista += personas
    }


    Column {
        Row {
            Button(onClick = {  }) {

            }
            Text(text = numero)
        }
        Row {
            Text(text = "marcos")
        }
    }
}


