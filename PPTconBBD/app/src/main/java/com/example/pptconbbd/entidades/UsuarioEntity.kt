package com.example.pptconbbd.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Clase para la entidad Usuario
 */
@Entity(tableName = "Usuario_Entity")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    //La primaryKey se genera automaticamente
    var id:Int = 0,
    var username:String = "",
    var partidas:Int = 0,
    var victorias:Int = 0

)
