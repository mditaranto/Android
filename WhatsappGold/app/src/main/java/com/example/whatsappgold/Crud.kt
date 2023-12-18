package com.example.whatsappgold

import com.example.whatsappgold.Entity.MensajeEntity
import com.example.whatsappgold.Entity.UsuarioEntity
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

fun createUsuario(
    usuario: UsuarioEntity,
    db: DatabaseReference,
) {
    runBlocking { // Insert Contact
        val usuarios: MutableList<UsuarioEntity> = mutableListOf()
        val result = db.get().await()
        for (data in result.children) {
            val user = data.getValue(UsuarioEntity::class.java)
            if (user != null) {
                usuarios.add(user)
            }
        }
        if (!usuarios.contains(usuario)) {
            println("Usuario creado")
            if (usuario != null) {
                db.push().setValue(usuario)
        }
        }
    }

}

fun getAlias(
    db: DatabaseReference,
    dbUser: DatabaseReference,
    phoneNumber: String,
): MutableList<String> {
    val allalias: MutableList<String> = mutableListOf()
    runBlocking {
        val result = db.get().await()
        for (data in result.children) {
            val mensaje = data.getValue(MensajeEntity::class.java)
            if (mensaje != null && mensaje.telefonoSender != phoneNumber && mensaje.telefonoReceiver == phoneNumber) {
                val alias = getAliasByPhone(dbUser, mensaje.telefonoSender.toString())
                if (!allalias.contains(alias)) {
                    allalias.add(alias)
                }
            } else {
                if (mensaje != null && mensaje.telefonoReceiver != phoneNumber && mensaje.telefonoSender == phoneNumber) {
                    val alias = getAliasByPhone(dbUser, mensaje.telefonoReceiver.toString())
                    if (!allalias.contains(alias)) {
                        allalias.add(alias)
                    }
                }
            }
        }
    }
    return allalias
}

fun getAliasByPhone(
    db: DatabaseReference,
    phoneNumber: String,
): String {
    var alias = ""
    runBlocking {
        val result = db.get().await()
        for (data in result.children) {
            val user = data.getValue(UsuarioEntity::class.java)
            if (user != null) {
                if (user.phoneNumber == phoneNumber) {
                    alias = user.alias.toString()
                }
            }
        }
    }
    return alias
}

fun getPhoneByAlias(
    db: DatabaseReference,
    alias: String,
): String {
    var phoneNumber = ""
    runBlocking {
        val result = db.get().await()
        for (data in result.children) {
            val user = data.getValue(UsuarioEntity::class.java)
            if (user != null) {
                if (user.alias == alias) {
                    phoneNumber = user.phoneNumber.toString()
                }
            }
        }
    }
    return phoneNumber
}

fun getAllMessages(
    db: DatabaseReference,
    phoneNumber: String,
    phoneFriend: String,
): MutableList<MensajeEntity> {
    val allMessages: MutableList<MensajeEntity> = mutableListOf()
    runBlocking {
        val result = db.get().await()
        for (data in result.children) {
            val mensaje = data.getValue(MensajeEntity::class.java)
            if (mensaje != null && ((mensaje.telefonoSender == phoneNumber && mensaje.telefonoReceiver == phoneFriend) || (mensaje.telefonoSender == phoneFriend || mensaje.telefonoReceiver == phoneNumber))) {
                allMessages.add(mensaje)
            }
        }
    }
    return allMessages
}

fun createMessage(
    mensaje: MensajeEntity,
    db: DatabaseReference,
) {
    runBlocking { // Insert Contact
            if ((mensaje.message.toString().isNotEmpty())) {
                db.push().setValue(mensaje) }
    }

}


