package com.example.mattichat.FireStore

import com.example.mattichat.Entidades.Chats
import com.example.mattichat.Entidades.Mensajes
import com.example.mattichat.Entidades.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirestoreManager {
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    //Usuario
    suspend fun createUser(user: User) {
        usersCollection.add(user).await()
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun login(userIn: User): Boolean {
        var logeado = CompletableDeferred<Boolean>()
        val subscription = usersCollection.addSnapshotListener{ snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val userDocument = querySnapshot.documents.find {
                    it.get("phoneNumber") == userIn.phoneNumber }
                if (userDocument == null) {
                    GlobalScope.launch {
                        createUser(userIn)
                        logeado.complete(true)
                    }
                } else if (userDocument.get("password") == userIn.password) {
                    logeado.complete(true)

                } else {
                    logeado.complete(false)
                }
            }
        }

        return logeado.await()
    }

    //Chats
    suspend fun createChat(chat: Chats, chats: List<Chats>) {
        if (chats.find { (it.phone1 == chat.phone1 && it.phone2 == chat.phone2) ||
                    (it.phone1 == chat.phone2 && it.phone2 == chat.phone1) } == null) {
            val alias1 = getAliasByPhone(chat.phone1!!)
            alias1.collect { alias1 ->
                val alias2 = getAliasByPhone(chat.phone2!!)
                alias2.collect { alias2 ->
                    chat.alias1 = alias1
                    chat.alias2 = alias2
                    firestore.collection("chats").add(chat).await()

                }
            }

        }
    }

    fun getAllChats(userPhone: String): Flow<List<Chats>> = callbackFlow {
        val chatsCollection = firestore.collection("chats")

        val subscription1 = chatsCollection.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val chats = mutableListOf<Chats>()
                for (document in querySnapshot.documents) {
                    val chat = document.toObject(Chats::class.java)
                    if (chat?.phone1 == userPhone || chat?.phone2 == userPhone) {
                        chats.add(chat)
                    }
                }
                trySend(chats).isSuccess
            }
        }

        awaitClose {
            subscription1.remove()
        }
    }

    fun getAliasByPhone(phone: String): Flow<String> = callbackFlow {
        val usersCollection = firestore.collection("users")
            .whereEqualTo("phoneNumber", phone)

        val subscription = usersCollection.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val users = mutableListOf<User>()
                for (document in querySnapshot.documents) {
                    val user = document.toObject(User::class.java)
                    user?.let { users.add(it) }
                }
                if (users.size != 0) {
                    if (users[0].alias != null) {
                        trySend(users[0].alias.toString()).isSuccess
                    } else {
                        trySend(phone).isSuccess
                    }
                } else {
                    trySend(phone).isSuccess
                }
            }
        }
        awaitClose { subscription.remove() }
    }

    //Mensajes
    suspend fun createMessage(mensaje: Mensajes) {
        val chatsCollection = firestore.collection("Mensajes")
        val chat = chatsCollection.add(mensaje).await()
    }

    fun getAllMessages(phone1: String, phone2: String): Flow<List<Mensajes>> = callbackFlow {
        val msgCollection = firestore.collection("Mensajes").orderBy("timestamp")

        val subscription = msgCollection.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val mensajes = mutableListOf<Mensajes>()
                for (document in querySnapshot.documents) {
                    val msg = document.toObject(Mensajes::class.java)
                    if ((msg?.telefonoSender == phone1 && msg.telefonoReceiver == phone2) || (msg?.telefonoSender == phone2 && msg.telefonoReceiver == phone1)) {
                        mensajes.add(msg)
                    }
                }
                trySend(mensajes).isSuccess
            }
        }

        awaitClose {
            subscription.remove()
        }
    }

    //Alias
    suspend fun cambiarAlias(phone: String, alias: String) {
        val usersCollection = firestore.collection("users")
            .whereEqualTo("phoneNumber", phone)

        val chatsCollection = firestore.collection("chats")

        val chats = chatsCollection.get().await().documents
        for (chat in chats) {
            if (chat.get("phone1") == phone) {
                chat.reference.update("alias1", alias).await()
            } else if (chat.get("phone2") == phone) {
                chat.reference.update("alias2", alias).await()
            }
        }

        val user = usersCollection.get().await().documents[0].reference

        user.update("alias", alias).await()


    }
}