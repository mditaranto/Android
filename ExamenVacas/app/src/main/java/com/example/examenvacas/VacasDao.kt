package com.example.examenvacas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.examenvacas.Entidades.tMilkEntity
import com.example.examenvacas.Entidades.tVacaEntity

@Dao
interface VacasDao {

    @Query("SELECT * FROM tVaca")
    suspend fun getAllVacas(): MutableList<tVacaEntity>  // Función que devuelve todas las tareas de la base de datos en una lista Mutable.

    @Insert
    suspend fun addVaca(vaca : tVacaEntity):Long    // Función que añade una tarea, la que se pasa por parámetro, y devuelve el id insertado.                                                          // Devuelve Long porque la cantidad de datos guardada puede ser muy alto.

    @Query("SELECT * FROM tVaca where idVaca like :id")
    suspend fun getVacaById(id: Long): tVacaEntity        // Función que busca tareas por id (debe ser Long, no Int)

    @Query("UPDATE tVaca SET litrosActuales = litrosActuales + :litros where idVaca like :id")
    suspend fun updateVaca(id: Long, litros:Int):Int         // Función que actualiza una tarea y devuelve

    @Query("UPDATE tVaca SET litrosActuales = 0 where idVaca like :id")
    suspend fun vacaOrdeñada(id: Long):Int         // Función que actualiza una tarea y devuelve

    //Ahora de tMilk
    @Insert
    suspend fun addMilk(milk : tMilkEntity):Long    // Función que añade una tarea, la que se pasa por parámetro, y devuelve el id insertado.                                                          // Devuelve Long porque la cantidad de datos guardada puede ser muy alto.

    @Query("SELECT * FROM tMilk where idVaca like :id")
    suspend fun getMilkByIdVaca(id: Long): tMilkEntity        // Función que busca tareas por id (debe ser Long, no Int)

}