package com.example.piedrapapelotijeras

import com.example.piedrapapelotijeras.entity.UsuarioEntity
import kotlinx.coroutines.runBlocking
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

/**
 * Class that contains the CRUD functions. It uses the DAO although this one is like "extended" with
 * comprobations.
 */
class Crud {
    // Companion object is like a static class
    companion object {
        fun getContactos(
            db: DatabaseReference,
        ): MutableList<UsuarioEntity> {
            val usuarios: MutableList<UsuarioEntity> = mutableListOf()
            runBlocking {
                usuarios.clear()
                var contador = 0
                val result = db.get().await()
                for (data in result.children) {
                    val usuario = data.getValue(UsuarioEntity::class.java)
                    if (usuario != null) {
                        usuarios.add(usuario)
                        contador++
                    }

                }
            }

            return usuarios
        }

        /**
         * Function that creates a Contact.
         * @param username The Contact's name.
         * @param telefono The Contact's phone.
         * @param identification The Contact's identification.
         * @param db The database's viewModel.
         */
        fun createUsuario(
            usuario: UsuarioEntity,
            db: DatabaseReference
        ) {
            val newId = getLastId(db)
            runBlocking { // Insert Contact
                db.child(newId.toString()).setValue(usuario)
            }

        }

        /**
         * Function that gets the last Contact's id.
         * @param db The database's viewModel.
         */
        private fun getLastId(
            db: DatabaseReference
        ): String {
            var id = 0
            runBlocking {
                val contactos = db.get().await()


                for (data in contactos.children) {
                    if (id.toString() != data.key) {
                        break
                    }; id++
                }
            }
            return id.toString()

        }
    }

}