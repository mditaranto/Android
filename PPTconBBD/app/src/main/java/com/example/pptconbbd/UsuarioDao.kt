package com.example.pptconbbd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pptconbbd.entidades.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM Usuario_Entity")
    suspend fun getAllUsuario(): MutableList<UsuarioEntity>  // Función que devuelve todas las tareas de la base de datos en una lista Mutable.

    @Insert
    suspend fun addUsuario(usuarioEntity : UsuarioEntity):Long    // Función que añade una tarea, la que se pasa por parámetro, y devuelve el id insertado.                                                          // Devuelve Long porque la cantidad de datos guardada puede ser muy alto.

    @Query("SELECT * FROM Usuario_Entity where username like :username")
    suspend fun getUsuarioByUsername(username: String): UsuarioEntity        // Función que busca tareas por id (debe ser String)

    @Update
    suspend fun updateUsuario(usuario: UsuarioEntity):Int         // Función que actualiza una tarea y devuelve

    @Delete
    suspend fun deleteUsuario(usuario: UsuarioEntity):Int         // Función que borra una tarea y devuelve

}