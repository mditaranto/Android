package com.example.examenvacas.Entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ("tMilk"))
data class tMilkEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var idVaca:Long = 0,
    var fecha:String = "",
    var litrosExtraidos:Int = 0
)
