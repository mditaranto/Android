package com.example.whatsappgold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.whatsappgold.ui.theme.WhatsappGoldTheme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {

    private val dbUser = FirebaseDatabase.getInstance().getReference("Users")
    private val dbChat = FirebaseDatabase.getInstance().getReference("Mensajes")

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
                        composable("chatList/{phoneNumber}", arguments = listOf(navArgument("phoneNumber") {
                            type = NavType.StringType
                        })) {
                            chatList(navController, dbUser, dbChat, it.arguments?.getString("phoneNumber") ?: "Invitado")

                        }
                        composable("ChatScreen/{phoneNumber}/{alias}", arguments = listOf(navArgument("phoneNumber") {
                            type = NavType.StringType
                        }, navArgument("alias") {
                            type = NavType.StringType
                        })) {
                            ChatScreen(navController, dbChat,dbUser, it.arguments?.getString("phoneNumber") ?: "Invitado", it.arguments?.getString("alias") ?: "Invitado")
                        }
                    }
                }
            }
        }
    }
}
