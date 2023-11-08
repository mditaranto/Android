package com.example.pptconbbd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pptconbbd.entidades.UsuarioEntity

/**
 * Clase para crear el Room de la BBD
 */
@Database(entities = arrayOf(UsuarioEntity::class), version = 1)
abstract class UsuariosDatabase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuarioDao
}