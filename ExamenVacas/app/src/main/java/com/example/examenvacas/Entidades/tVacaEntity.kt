package com.example.examenvacas.Entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ("tVaca"))
data class tVacaEntity(
    @PrimaryKey(autoGenerate = true)
    var idVaca:Long = 0,
    var litrosActuales:Int = 0,
)
