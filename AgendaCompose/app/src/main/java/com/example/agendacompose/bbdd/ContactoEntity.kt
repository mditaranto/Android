package com.example.agendacompose.bbdd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactoTable")
data class ContactoEntity (
    @PrimaryKey var phone: Int,
    var name: String,
    var genre: Int
)