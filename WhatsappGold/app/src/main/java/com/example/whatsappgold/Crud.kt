package com.example.whatsappgold

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

