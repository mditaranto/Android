package com.example.agendacompose.bbdd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactoDao {
    @Query("SELECT * FROM contactoTable")
    suspend fun getAllPuntuaciones(): MutableList<ContactoEntity>

    @Insert
    suspend fun insertPuntuacion(puntuacion: ContactoEntity)

    @Update
    suspend fun updatePuntuacion(puntuacion: ContactoEntity)

    @Delete
    suspend fun deletePuntuacion(puntuacion: ContactoEntity)
}