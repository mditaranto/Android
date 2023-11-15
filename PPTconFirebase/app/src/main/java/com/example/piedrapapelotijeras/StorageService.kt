package com.example.piedrapapelotijeras

import com.example.piedrapapelotijeras.entity.UsuarioEntity

interface StorageService {
    fun addListener(
        userId: String,
        onDocumentEvent: (Boolean, UsuarioEntity) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun removeListener()
    fun getUser(userId: Int, onError: (Throwable) -> Unit, onSuccess: (UsuarioEntity) -> Unit)
    fun saveUser(user: UsuarioEntity, onResult: (Throwable?) -> Unit)
    fun updateUser(user: UsuarioEntity, onResult: (Throwable?) -> Unit)
    fun deleteUser(userId: Int, onResult: (Throwable?) -> Unit)
}
