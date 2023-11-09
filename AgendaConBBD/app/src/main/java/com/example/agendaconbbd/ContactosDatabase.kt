package com.example.agendaconbbd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agendaconbbd.Entity.ContactosEntity

@Database(entities = arrayOf(ContactosEntity::class), version = 1)
abstract class ContactosDatabase : RoomDatabase() {
    abstract fun ContactosDao(): ContactosDao
}