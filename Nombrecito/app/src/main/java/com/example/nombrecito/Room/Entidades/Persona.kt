package com.example.nombrecito.Room.Entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ("tPersona"))
data class Persona(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nombre: String = "",
    var apellidos: String = "",
    var DNI: String = "",
    var edad: Int = 0,
    var telefono: String = "",
)
