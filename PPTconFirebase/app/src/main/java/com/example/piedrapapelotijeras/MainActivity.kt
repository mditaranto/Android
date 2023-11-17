package com.example.piedrapapelotijeras

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.piedrapapelotijeras.ui.theme.PiedraPapelOTijerasTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

    private val db = FirebaseDatabase.getInstance().getReference("usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hacemos el navController
        setContent {
            val navController = rememberNavController()
            Column(

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Empezara en la vista Login
                NavHost(
                    navController = navController,
                    startDestination = "Login"
                ) {
                    //Vista Login
                    composable(route="Login") { Login(navController, db) }
                    //Esta vista tiene un parametro de entrada desde otra vista ("user")
                    composable(route="Juego/{User}", arguments = listOf(navArgument("User") {
                        type = NavType.StringType
                    })) {
                        //El argumento que recibe es "User" y si no se asigna el valor "Invitado"
                        Juego(navController, it.arguments?.getString("User") ?: "Invitado", db)
                    }
                    //Vista Estadisticas
                    composable(route="Estadisticas") { Estadisticas(navController, db)}

                }

            }
            db.addValueEventListener(object : ValueEventListener { // Add a listener to the database to update the list when a contact is created/modified/deleted
                override fun onDataChange(snapshot: DataSnapshot) {
                    // If the current screen is not Contactos, don't update the list
                    if (navController.currentDestination?.route == "Estadisticas") {
                        navController.navigate("Estadisticas") // Update the list
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })

        }
    }
}


