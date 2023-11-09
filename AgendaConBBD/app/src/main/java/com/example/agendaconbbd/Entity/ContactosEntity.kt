package com.example.agendaconbbd.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ("Contactos_entity"))
data class ContactosEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name:String = "",
    var tlfno:String = "",
    var generoMasc:Boolean = true
)


