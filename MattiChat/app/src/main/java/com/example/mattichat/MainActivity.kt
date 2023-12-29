package com.example.mattichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mattichat.Entidades.User
import com.example.mattichat.FireStore.FirestoreManager
import com.example.mattichat.Vistas.LoginScreen
import com.example.mattichat.Vistas.chatScreen
import com.example.mattichat.Vistas.chatsScreen
import com.example.mattichat.ui.theme.MattiChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val firestoreManager = FirestoreManager()
            MattiChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController,
                        startDestination = "LoginScreen") {
                        composable("LoginScreen") {
                            LoginScreen(firestoreManager, navController)
                        }
                        composable("chatsScreen/{userPhone}", arguments = listOf(navArgument("userPhone") {
                            type = NavType.StringType})) {
                            chatsScreen(firestoreManager, it.arguments?.getString("userPhone") ?: "000000000", navController)
                        }
                        composable("chatScreen/{userPhone}/{otherPhone}", arguments = listOf(navArgument("userPhone") {
                            type = NavType.StringType}, navArgument("otherPhone") {
                            type = NavType.StringType})) {
                            chatScreen(firestoreManager, navController, it.arguments?.getString("userPhone") ?: "000000000", it.arguments?.getString("otherPhone") ?: "000000000")
                        }
                    }
                }
            }
        }
    }
}
