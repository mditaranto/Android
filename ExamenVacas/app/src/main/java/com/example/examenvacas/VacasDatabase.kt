package com.example.examenvacas

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examenvacas.Entidades.tMilkEntity
import com.example.examenvacas.Entidades.tVacaEntity

@Database(entities = arrayOf(tVacaEntity::class, tMilkEntity::class), version = 1)
abstract class VacasDatabase : RoomDatabase() {
    abstract fun VacasDao(): VacasDao
}