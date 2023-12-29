package com.example.mattichat.Vistas

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mattichat.Entidades.Chats
import com.example.mattichat.Entidades.User
import com.example.mattichat.FireStore.FirestoreManager
import com.example.mattichat.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun chatsScreen(firestore: FirestoreManager, userPhone: String, navController: NavController) {
    val chats by firestore.getAllChats(userPhone).collectAsState(emptyList())

    var showCreateChat by remember { mutableStateOf(false) }

    var showEditAlias by remember { mutableStateOf(false) }

    var numero by remember {
        mutableStateOf("")
    }

    if (showEditAlias) {
        showEditAlias = EditAliasDialog(firestore, userPhone)
    }

    if (showCreateChat) {
        AlertDialog(onDismissRequest = {
            showCreateChat = false
        }, confirmButton = {
            TextButton(onClick = {
                createChat(
                    Chats(phone1 = userPhone, phone2 = numero), chats
                ); showCreateChat = false; numero = ""
            }) {
                Text(text = "Crear")
            }
        }, dismissButton = {
            TextButton(onClick = { showCreateChat = false }) {
                Text(text = "Cancelar")
            }
        }, title = {
            Text(text = "Crear chat")
        }, text = {
            Column {
                Row {
                    Text(text = "Alias: ")
                    TextField(
                        value = numero,
                        onValueChange = { numero = it },
                        placeholder = { Text(text = "Telefono") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }
            }
        })
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MattiChat") },
                actions = {
                    IconButton(onClick = { showEditAlias = true }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
        FloatingActionButton(
            onClick = { showCreateChat = true },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Añadir chat"
            )

        }
    }, content = {
        Column {
            Spacer(modifier = Modifier.height(56.dp))
            if (chats.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No se encontraron \nContactos",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Thin,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn() {
                    items(chats.size) { chat ->
                        if (userPhone == chats[chat].phone1) {
                            Card(
                                modifier = Modifier
                                    .padding(
                                        start = 15.dp, end = 15.dp, top = 15.dp, bottom = 0.dp
                                    )
                                    .fillMaxWidth()
                                    .clickable(onClick = {
                                        navController.navigate("chatScreen/${chats[chat].phone1}/${chats[chat].phone2}")
                                    })
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(modifier = Modifier.weight(3f)) {
                                        Text(
                                            text = chats[chat].alias2.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }

                        } else {
                            Card(
                                modifier = Modifier
                                    .padding(
                                        start = 15.dp, end = 15.dp, top = 15.dp, bottom = 0.dp
                                    )
                                    .fillMaxWidth()
                                    .clickable(onClick = {
                                        navController.navigate("chatScreen/${chats[chat].phone2}/${chats[chat].phone1}")
                                    })
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(modifier = Modifier.weight(3f)) {
                                        Text(
                                            text = chats[chat].alias1.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    })
}

@Composable
fun EditAliasDialog(
    firestore: FirestoreManager,
    userPhone: String,

): Boolean {

    var showEditAlias by remember { mutableStateOf(true) }

    var alias by remember {
        mutableStateOf("")
    }

    AlertDialog(onDismissRequest = {
    }, confirmButton = {
        TextButton(onClick = {
            GlobalScope.launch {
                firestore.cambiarAlias(userPhone, alias)
                showEditAlias = false
            }
        }) {
            Text(text = "Editar")
        }
    }, dismissButton = {
        TextButton(onClick = { showEditAlias = false }) {
            Text(text = "Cancelar")
        }
    }, title = {
        Text(text = "Editar alias")
    }, text = {
        Column {
            Row {
                Text(text = "Alias: ")
                TextField(
                    value = alias,
                    onValueChange = { alias = it },
                    placeholder = { Text(text = "Alias") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
            }
        }
    })

    return showEditAlias
}

fun createChat(chat: Chats, chats: List<Chats>) {
    GlobalScope.launch {
        FirestoreManager().createChat(chat, chats)
    }
}



