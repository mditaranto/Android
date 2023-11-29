package com.example.whatsappgold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappgold.ui.theme.WhatsappGoldTheme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {

    private val dbUser = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            WhatsappGoldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(navController, dbUser)
                        }
                        composable("Chats") {
                            Chats(navController, dbUser)
                        }
                        composable("Chat") {
                            Chat(navController, dbUser)
                        }
                    }
                }
            }
        }
    }
}
