package com.example.lolvoices.Vistas

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lolvoices.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CampeonesScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            // Toolbar con 2 botones
            TopAppBar(
                modifier = Modifier.background(Color(0x00031118)),
                title = { Text(text = "LoLVoices") },
                actions = {
                    IconButton(onClick = { navController.navigate("FavoritosScreen") }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar")
                    }
                    IconButton(onClick = { navController.navigate("JueguitoScreen") }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    }
                }
            )
        },
        content = {
            //Nombres de campeones de ejemplo
            val campeones = List(30) { "Campeón $it" }

            Column {
                Spacer(modifier = Modifier.height(60.dp))

                Box(modifier = Modifier.fillMaxSize()) {
                    // Fondo de pantalla
                    Image(painter = painterResource(id = R.drawable.fondo),
                        contentDescription = "Fondo de pantalla", contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // LazyColumn con 3 columnas para mostrar los datos actualizados
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        // LazyVerticalGrid con 3 columnas para mostrar los datos de cada tipo
                        items(campeones.chunked(3)) { rowData ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                // Rellenar la fila con celdas vacías si no hay suficientes datos
                                val filledRowData = rowData + List(3 - rowData.size) { null }

                                filledRowData.forEach { item ->
                                    IconButton(
                                        onClick = { /* Acción al hacer clic en el botón */ },
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(8.dp)
                                            // Estilo del botón
                                            .background(
                                                color = if (item == null) Color.Transparent else Color.Blue,
                                                shape = CircleShape
                                            )
                                    ) {
                                        item?.let {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "Play",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}