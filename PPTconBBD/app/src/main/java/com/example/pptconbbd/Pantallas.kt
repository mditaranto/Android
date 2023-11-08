package com.example.pptconbbd

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Scroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pptconbbd.entidades.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {

    var usuario by rememberSaveable {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        )    {

        Row (modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {

            Text(text = "usuario: ")
            OutlinedTextField(value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") })
            
        }
        
        Row (modifier = Modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center) {
            
            Button(onClick = {
                navController.navigate("Juego/$usuario")
            }) {
                Text("Jugar")}
        }

        Row (modifier = Modifier,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center) {

            Button(onClick = {
                navController.navigate("Estadisticas")
            }) {
                Text("Estadisticas")}
        }
    }
}

@Composable
fun Juego(navController: NavController, user: String) {
    //Lista donde guardo las fotos que voy a utilizar
    val lista = listOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.piedra,
        R.drawable.tijeras,
        R.drawable.papel
    )

    val contexto = LocalContext.current
    var partidas by remember {
        mutableStateOf(0)
    }

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
        partidas += 1
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

        if (victoriasM == 3) {
            Toast.makeText(contexto,"Se acabo la partida", Toast.LENGTH_SHORT).show()
            addUsuario(UsuarioEntity(username = user, victorias = victoriasJ, partidas = partidas))
            victoriasM = 0
            navController.navigate("Login")

        }

    }

    //Cuando la maquina gane 3 veces, se acabo


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


@Composable
fun Estadisticas(navController: NavController) {

    var lista by remember { mutableStateOf<List<UsuarioEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        val usuarios = withContext(Dispatchers.IO) {
            MainActivity.database.UsuarioDao().getAllUsuario()
        }
        lista = usuarios
    }

    Image(painter = painterResource(id = R.drawable.fondolista), contentDescription = "Fondo",
        modifier = Modifier.fillMaxSize())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row (modifier = Modifier.fillMaxWidth()){
            Column (modifier = Modifier.weight(1f)){
                Text(text = "Usuario  ", fontSize = 30.sp)
            }
            Column (modifier = Modifier.weight(1f)){
                Text(text = "Partidas  ", fontSize = 30.sp)
            }
            Column (modifier = Modifier.weight(1f)){
                Text(text = "Victorias  ", fontSize = 30.sp)
            }

        }

        for (a in lista) {
            // Contenido centrado verticalmente
            Row(
                modifier = Modifier
                    .weight(1f) // Ocupa el espacio vertical en el centro
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                    Column(modifier = Modifier.weight(1f)) {

                        val texto = "${a.username}"
                        Text(text = texto, fontSize = 30.sp)

                    }

                    Column(modifier = Modifier.weight(1f)) {

                        val texto = "${a.partidas}"
                        Text(text = texto, fontSize = 30.sp)

                    }

                    Column(modifier = Modifier.weight(1f)) {

                        val texto = "${a.victorias}"
                        Text(text = texto, fontSize = 30.sp)

                    }

            }
        }

        // Botón abajo a la derecha
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = { navController.navigate("Login") }) {
                Text(text = "Volver")
            }
        }
    }

    }


fun addUsuario(usuario: UsuarioEntity)= runBlocking{  // Corrutina que añade una tarea a la lista
    launch {
        val id = MainActivity.database.UsuarioDao().addUsuario(usuario)   // Inserta una tarea nueva

    }
}

