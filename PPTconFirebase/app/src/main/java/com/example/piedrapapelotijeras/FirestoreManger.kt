package com.example.piedrapapelotijeras

import android.content.Context
import com.example.piedrapapelotijeras.entity.UsuarioEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreManger(context: Context) {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun addUsuario(user: UsuarioEntity) {
        firestore.collection("usuarios").add(user).await()
    }

    suspend fun updateUsuario(user: UsuarioEntity) {
        val userRef = user.id.let { firestore.collection("usuarios").document(it.toString()) }
        userRef.set(user).await()
    }

    suspend fun deleteUsuario(idUser: Int) {
        val userRef = firestore.collection("usuarios").document(idUser.toString())
        userRef.delete().await()

    }

}