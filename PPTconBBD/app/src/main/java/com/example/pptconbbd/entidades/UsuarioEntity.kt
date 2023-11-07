package com.example.pptconbbd.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario_Entity")
data class UsuarioEntity(
    @PrimaryKey()
    var username:String = "",
    var victorias:Int = 0

)
