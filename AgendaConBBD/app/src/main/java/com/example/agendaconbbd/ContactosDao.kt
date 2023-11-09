package com.example.agendaconbbd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.agendaconbbd.Entity.ContactosEntity

@Dao
interface ContactosDao {

    @Query("SELECT * FROM Contactos_entity")
    suspend fun getAllContactos(): MutableList<ContactosEntity>  // Función que devuelve todas las tareas de la base de datos en una lista Mutable.

    @Insert
    suspend fun addContactos(contactosEntity : ContactosEntity):Long    // Función que añade una tarea, la que se pasa por parámetro, y devuelve el id insertado.                                                          // Devuelve Long porque la cantidad de datos guardada puede ser muy alto.

    @Query("SELECT * FROM Contactos_entity where id like :id")
    suspend fun getContactosById(id: Long): ContactosEntity        // Función que busca tareas por id (debe ser Long, no Int)

    @Update
    suspend fun updateContactos(contac: ContactosEntity):Int         // Función que actualiza una tarea y devuelve

    @Delete
    suspend fun deleteContactos(contac: ContactosEntity):Int         // Función que borra una tarea y devuelve
}