package com.example.pptconbbd

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pptconbbd.ui.theme.PPTconBBDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController, modifier: Modifier = Modifier) {

    var usuario by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        )    {

        Row (modifier = Modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center) {

            Text(text = "usuario: ")
            OutlinedTextField(value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") })
            
        }
        
        Row (modifier = Modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center) {
            
            Button(onClick = {navController.navigate("Juego")
            }) {
                Text("Jugar")}
        }
    }
}

@Composable
fun Juego(navController: NavController, modifier: Modifier = Modifier) {
    //Lista donde guardo las fotos que voy a utilizar
    val lista = listOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.piedra,
        R.drawable.tijeras,
        R.drawable.papel
    )

    val contexto = LocalContext.current

    //Variable remember de la eleccion del jugador
    var eleccionJ by remember {
        mutableStateOf(0)
    }

    //Variable remember de la eleccion de la maquina (aleatorio)
    var eleccionM by remember {
        mutableStateOf(0)
    }

    //Variable remember boolean que guarda si el jugador ha elegido o no
    var turno by remember {
        mutableStateOf(true)
    }

    //Variable remember que guarda las victorias de la maquina
    var victoriasM by remember {
        mutableStateOf(0)
    }

    //Variable remember que guarda las victorias del jugador
    var victoriasJ by remember {
        mutableStateOf(0)
    }

    //Se comprueba si el jugador ha elegido ya
    if (turno) {

    } else {
        //asigna un valor aleatorio a la eleccion de la maquina
        val aleat = (1..3).random()
        eleccionM = aleat

        //Devuelve el turno al jugador
        turno = true

        //Comprueba quien ha ganado la ronda, se informa mediante un toast y se añade a
        //la variable del ganador
        if (eleccionJ == eleccionM) {
            Toast.makeText(contexto,"Ronda empatada", Toast.LENGTH_SHORT).show()
        } else if (eleccionM == 2 && eleccionJ == 3) {
            Toast.makeText(contexto,"La maquina gana la ronda", Toast.LENGTH_SHORT).show()
            victoriasM += 1
        } else if (eleccionM == 3 && eleccionJ == 1) {
            Toast.makeText(contexto,"La maquina gana la ronda", Toast.LENGTH_SHORT).show()
            victoriasM += 1
        } else if (eleccionM == 1 && eleccionJ == 2) {
            Toast.makeText(contexto,"La maquina gana la ronda", Toast.LENGTH_SHORT).show()
            victoriasM += 1
        } else {
            Toast.makeText(contexto,"El jugador gana la ronda", Toast.LENGTH_SHORT).show()
            victoriasJ += 1
        }

    }

    //Cuando la maquina gane 3 veces, se acabo
    if (victoriasM == 3) {
        Toast.makeText(contexto,"Se acabo la partida", Toast.LENGTH_SHORT).show()
        navController.navigate("Login")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(Color.Gray)
                .rotate(180F)
        ) {

            Image(painter = painterResource(id = R.drawable.fondocespes),
                contentDescription = "fondo",
                contentScale = ContentScale.FillBounds)

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.piedra),
                    contentDescription = "piedra",
                    Modifier.size(120.dp,120.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.tijeras),
                    contentDescription = "tijeras",
                    Modifier.size(120.dp,120.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.papel),
                    contentDescription = "papel",
                    Modifier.size(120.dp,120.dp)
                )
            }
        } // Contenido de la parte superior


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(Color.Gray)
        ) {
            Image(painter = painterResource(id = R.drawable.fondonthe),
                contentDescription = "fondo",
                contentScale = ContentScale.FillBounds)
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    //recoge la foto de la lista segun la seleccion
                    painter = painterResource(id = lista[eleccionJ]),
                    contentDescription = "tijeras",
                    Modifier.size(120.dp,120.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.espada),
                    contentDescription = "espada",
                    Modifier.size(120.dp,120.dp)
                )
                Image(
                    //recoge la foto de la lista segun la seleccion
                    painter = painterResource(id = lista[eleccionM]),
                    contentDescription = "papel",
                    Modifier.size(120.dp,120.dp)
                )
            }

            // Contenido de la parte central
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(Color.Gray)

        ) {

            Image(painter = painterResource(id = R.drawable.fondocespes),
                contentDescription = "fondo",
                contentScale = ContentScale.FillBounds)
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.piedra),
                    contentDescription = "piedra",
                    Modifier
                        .size(120.dp, 120.dp)
                        //Al pulsar, se le asigna un valor a la eleccion del jugador y se acaba
                        //el turno
                        .clickable {
                            if (turno) {
                                eleccionJ = 1
                            }; turno = false
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.tijeras),
                    contentDescription = "tijeras",
                    Modifier
                        .size(120.dp, 120.dp)
                        //Al pulsar, se le asigna un valor a la eleccion del jugador y se acaba
                        //el turno
                        .clickable {
                            if (turno) {
                                eleccionJ = 2
                            }; turno = false
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.papel),
                    contentDescription = "papel",
                    Modifier
                        .size(120.dp, 120.dp)
                        //Al pulsar, se le asigna un valor a la eleccion del jugador y se acaba
                        //el turno
                        .clickable {
                            if (turno) {
                                eleccionJ = 3
                            }; turno = false
                        }
                )


            }
            // Contenido de la parte inferior
        }
    }
}

