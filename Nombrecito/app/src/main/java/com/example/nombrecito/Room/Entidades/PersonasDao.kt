package com.example.nombrecito.Room.Entidades

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PersonasDao {

    @Query("SELECT * FROM tPersona")
    suspend fun getAllPersonas(): MutableList<Persona>  // Función que devuelve todas las tareas de la base de datos en una lista Mutable.

    @Query("DELETE FROM tPersona WHERE id like :id")
    suspend fun deletePersona(id: Int): Int

    @Query("SELECT * FROM tPersona where id like :id")
    suspend fun getPersonaById(id: Int): Persona        // Función que busca tareas por id (debe ser Long, no Int)

}