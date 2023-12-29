package com.example.mattichat.Vistas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mattichat.Entidades.Mensajes
import com.example.mattichat.FireStore.FirestoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chatScreen(firestore: FirestoreManager, navController: NavController, userPhone: String, OtherPhone: String) {

    val mensajes by firestore.getAllMessages(userPhone, OtherPhone).collectAsState(emptyList())
    mensajes.sortedBy { it.timestamp }
    var escrito by remember {
        mutableStateOf("")
    }

    var showEditAlias by remember {
        mutableStateOf(false)
    }

    if (showEditAlias) {
        showEditAlias = EditAliasDialog(firestore, userPhone)
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var horaactual by remember {
        mutableStateOf("")
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
        content = {
            LazyColumn {
                items(mensajes.size) { index ->
                    val enviado = mensajes[index].telefonoSender == userPhone
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(if (enviado) Alignment.CenterEnd else Alignment.CenterStart)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 48f,
                                        topEnd = 48f,
                                        bottomStart = if (enviado) 0f else 48f,
                                        bottomEnd = if (enviado) 48f else 0f
                                    )
                                )
                                .background(Color.Blue)
                                .padding(16.dp)
                        ) {
                            Text(text = mensajes[index].message.toString())
                        }
                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(
                    value = escrito,
                    onValueChange = { escrito = it },
                    label = { Text("Escribe un mensaje") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    horaactual = formatter.format(LocalDateTime.now()).toString()
                    GlobalScope.launch {
                        firestore.createMessage(
                            Mensajes(
                                message = escrito,
                                timestamp = horaactual,
                                telefonoSender = userPhone,
                                telefonoReceiver = OtherPhone,
                                state = 0
                            )
                        )
                    }
                }) {
                    Text("Enviar")
                }
            }
        })
}


