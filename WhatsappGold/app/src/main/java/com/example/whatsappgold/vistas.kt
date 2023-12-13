package com.example.whatsappgold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.res.painterResource

@Composable
fun Chat(navController: NavController, dbUser: DatabaseReference) {
    Text(text = "Chat")
}

@Composable
fun Chats(navController: NavController, dbUser: DatabaseReference) {
    Text(text = "Chats")
}

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
            Button(onClick = { createUsuario(UsuarioEntity(phoneNumber, password, ""), dbUser); navController.navigate("Chats") }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5DE6E1))) {
                Text("Login")
            }
        }
    }
}

/**
 * Loading screen for the app.
 * It just contains the logo of the app for the time it's loading
 */
@Composable
fun Loading(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.android),
            contentDescription = "Logo;Loading...",
            modifier = Modifier
                .fillMaxSize(0.6F)
        )
    }
}

@Composable
fun chatList(navController: NavController, dbUser: DatabaseReference) {

}
