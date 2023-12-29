package com.example.mattichat.Entidades

data class Mensajes(
    val id: String? = null,
    val message: String? = "",
    val timestamp: String? = "",
    val telefonoSender: String? = "",
    val telefonoReceiver: String? = "",
    val state: Int? = 0
)
