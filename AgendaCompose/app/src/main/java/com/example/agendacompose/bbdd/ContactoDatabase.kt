package com.example.agendacompose.bbdd

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactoEntity::class], version = 1)
abstract class ContactoDatabase: RoomDatabase() {
abstract fun contactoDao(): ContactoDao
}