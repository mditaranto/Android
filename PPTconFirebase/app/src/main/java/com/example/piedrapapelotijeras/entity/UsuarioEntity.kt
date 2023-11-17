package com.example.piedrapapelotijeras.entity

data class UsuarioEntity(

    //La primaryKey se genera automaticamente
    var id:Int? = 0,
    var username:String? = "",
    var partidas:Int? = 0,
    var victorias:Int? = 0

)