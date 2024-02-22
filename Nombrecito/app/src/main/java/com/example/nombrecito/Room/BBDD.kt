package com.example.nombrecito.Room

import androidx.compose.runtime.Composable
import com.example.nombrecito.MainActivity
import com.example.nombrecito.Room.Entidades.Persona

suspend fun listaPersonas(): MutableList<Persona> {
    val listaPersona = MainActivity.database.PersonaDao().getAllPersonas()

    return listaPersona
}

