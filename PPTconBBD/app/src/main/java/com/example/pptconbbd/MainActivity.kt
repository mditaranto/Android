package com.example.pptconbbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room

import com.example.pptconbbd.ui.theme.PPTconBBDTheme

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var database: UsuariosDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database =
            Room.databaseBuilder(this, UsuariosDatabase::class.java, "usuarios-db").build()

        setContent {
            val navController = rememberNavController()
            var modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
                .padding(50.dp)
            Column(
                modifier=modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "Login"
                ) {
                    composable(route="Login") { Login(navController,modifier) }
                    composable(route="Juego") { Juego(navController, modifier)}

                }
            }

        }
    }
}

