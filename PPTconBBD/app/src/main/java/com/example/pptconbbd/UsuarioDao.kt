package com.example.pptconbbd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pptconbbd.entidades.UsuarioEntity

/**
 * Interfaz para el Dao de la BBD
 */
@Dao
interface UsuarioDao {

    @Query("SELECT * FROM Usuario_Entity ORDER BY victorias DESC LIMIT 5")
    suspend fun getAllUsuario(): MutableList<UsuarioEntity>  // Función que devuelve los Usuarios en una lista

    @Insert
    suspend fun addUsuario(usuarioEntity : UsuarioEntity):Long  // Función que añade un usuario, el que se pasa por parámetro, y devuelve el id insertado.                                                          // Devuelve Long porque la cantidad de datos guardada puede ser muy alto.

    @Query("SELECT * FROM Usuario_Entity where id like :id")
    suspend fun getUsuarioByUsername(id: Long): UsuarioEntity        // Función que busca usuarios por id (debe ser Long)

    @Update
    suspend fun updateUsuario(usuario: UsuarioEntity):Int         // Función que actualiza un usuario y devuelve Int

    @Delete
    suspend fun deleteUsuario(usuario: UsuarioEntity):Int         // Función que borra un usuario y devuelve Int

}