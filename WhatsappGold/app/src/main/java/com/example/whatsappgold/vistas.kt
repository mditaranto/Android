package com.example.whatsappgold

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.whatsappgold.Entity.UsuarioEntity
import com.google.firebase.database.DatabaseReference
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.whatsappgold.Entity.MensajeEntity
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, dbUser: DatabaseReference) {
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(onClick = { createUsuario(UsuarioEntity(phoneNumber, password, ""), dbUser); navController.navigate("chatList/$phoneNumber") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5DE6E1))) {
                Text("Login")
            }
        }
    }
}

@Composable
fun chatList(
    navController: NavController,
    dbUser: DatabaseReference,
    dbChat: DatabaseReference,
    phoneNumber: String
) {
    var chats: MutableList<String> = getAlias(dbChat,dbUser ,phoneNumber)
    LazyColumn {
        items(chats.size) { index ->
            Card (colors = CardDefaults.cardColors(
                containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable {
                        // Navigate to Chat sending the chat mobile number
                        navController.navigate("ChatScreen/$phoneNumber/${chats[index]}")
                    })
                {
                    Row (
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = chats[index])
                    }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    dbChat: DatabaseReference,
    dbUser: DatabaseReference,
    phoneNumber: String,
    alias: String,
) {

    var message by remember { mutableStateOf("") }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var horaactual by remember {
        mutableStateOf("")
    }

    val friendphone = getPhoneByAlias(dbUser, alias)
    var chats: MutableList<MensajeEntity> = getAllMessages(dbChat, phoneNumber, friendphone)
    LazyColumn {
        items(chats.size) { index ->
            val enviado = chats[index].telefonoSender == phoneNumber
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
                    Text(text = chats[index].message.toString())
                }
            }

        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Escribe un mensaje") },
            modifier = Modifier.weight(1f)
        )
        Button(onClick = {
            horaactual = formatter.format(java.time.LocalDateTime.now()).toString()
            createMessage(MensajeEntity(message = message, timestamp = horaactual, telefonoSender = phoneNumber, telefonoReceiver = friendphone, state = 0), dbChat);
            navController.navigate("chatList/$phoneNumber/$alias")
        }) {
            Text("Enviar")
        }
    }
}

