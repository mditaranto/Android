package com.example.agendacompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agendacompose.bbdd.*
import kotlinx.coroutines.runBlocking

@Composable
fun Formulario() {

}

@Composable
fun Contactos(
    navController: NavController,
    viewModel: ContactoDao
) {
    val contactos: List<ContactoEntity> = runBlocking {
        viewModel.getAllPuntuaciones()
    }

    LazyColumn {
        items(contactos.size) { contacto ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                for (contacto in contactos) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = contacto.name,
                                fontSize = 20.sp
                            )
                            Text(
                                text = contacto.phone.toString(),
                                fontSize = 16.sp
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    //navController.navigate("Formulario/${contacto.id}")
                                }
                        )
                    }
                }
            }

        }
    }

    AddButton(navController)
}

@Composable
fun Formulario(
navController: NavController,
    viewModel: ContactoDao
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    navController.navigate("Contactos")
                }
            )

        Text(
            text = "Crear Contacto",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(16.dp)
        )

        Button (
            onClick = {
                //viewModel.insertPuntuacion(PuntuacionesEntity("Test", 0, 0))
            },
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.blue)
            ),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Guardar",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun AddButton(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = "Add",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    navController.navigate("Formulario")
                }
        )
    }
}
