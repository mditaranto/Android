package com.example.nombrecito.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nombrecito.Room.Entidades.Persona
import com.example.nombrecito.Room.Entidades.PersonasDao

@Database(entities = arrayOf(Persona::class), version = 1)
abstract class CrearDatabase : RoomDatabase() {
    abstract fun PersonaDao(): PersonasDao
    abstract fun PersonaDao2(): PersonasDao
}