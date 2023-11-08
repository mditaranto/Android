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

/**
 * Funcion compose del login
 * Recibe como parametro un navController
 * Muestra una pantalla de Login donde puedes inserar un usuario
 * Con un boton navega a la pantalla de juego pasando como parametro el usuario
 * Con un boton navega a la pantalla de estadisticas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {

    //Variable donde se guarda el usuario
    var usuario by rememberSaveable {
        mutableStateOf("")
    }

    //Estructura de la vista
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
                //Navega hasta Juego
                //En caso de estar vacio, se establece el valor "Invitado"
                if (usuario.isEmpty()) {
                    usuario = "Invitado"
                }
                navController.navigate("Juego/$usuario")//la variable que se pasa como parametro a la otra vista
            }) {
                Text("Jugar")}
        }

        Row (modifier = Modifier,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center) {

            Button(onClick = {
                //Navega hasta Estadisticas
                navController.navigate("Estadisticas")
            }) {
                Text("Estadisticas")}
        }
    }
}

/**
 * Funcion compose del login
 * Recibe como parametro un navController
 * Muestra la pantalla de un Piedra, papel y tijeras
 * Al perder se vuelve a la vista Login
 */
@Composable
fun Juego(navController: NavController, user: String) {
    //Lista donde guardo las fotos que voy a utilizar
    val lista = listOf(
        R.drawable.ic_launcher_foreground,
        R.drawable.piedra,
        R.drawable.tijeras,
        R.drawable.papel
    )

    //Contexto
    val contexto = LocalContext.current

    //Variable para guardas las partidas totales jugadas
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
        //Se suma 1 a las partidas
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

        //Cuando la maquina gana 3 veces, se informa del fin
        //Se añade el jugador a la BBD y se navega al login
        if (victoriasM == 3) {
            Toast.makeText(contexto,"Se acabo la partida", Toast.LENGTH_SHORT).show()
            addUsuario(UsuarioEntity(username = user, victorias = victoriasJ, partidas = partidas))
            victoriasM = 0
            navController.navigate("Login")

        }

    }

    //Estructura de la vista
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

/**
 * Funcion compose de las estadisticas
 * Recibe como parametro un navController
 * Muestra la pantalla de una lista de los mejores 5 jugadores
 * con un boton vuelve a la vista Login
 */
@Composable
fun Estadisticas(navController: NavController) {
    //Variable que guarda el get de los usuarios
    var lista by remember { mutableStateOf<List<UsuarioEntity>>(emptyList()) }

    //Con una Corroutina damos valor a la variable usuarios
    LaunchedEffect(Unit) {
        val usuarios = withContext(Dispatchers.IO) {
            //Get de los usuarios
            MainActivity.database.UsuarioDao().getAllUsuario()
        }
        lista = usuarios
    }

    //Imagen de fondo
    Image(painter = painterResource(id = R.drawable.fondolista), contentDescription = "Fondo",
        modifier = Modifier.fillMaxSize())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Row superior
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

        //Para los usuarios de la lista
        for (a in lista) {
            var contador = 0
            // Contenido centrado verticalmente
            // Creamos un row con 3 columnas donde iran cada uno de los valores
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

                contador += 1
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
            //Navega a la vista Login
            Button(onClick = { navController.navigate("Login") }) {
                Text(text = "Volver")
            }
        }
    }

    }


/**
 * Funcion que agrega un usuario a la BBD
 * Tiene como parametros de entrada un objeto de tipo UsuarioEntity
 * Hace un Insert en la BBD con una corroutina
 */
fun addUsuario(usuario: UsuarioEntity)= runBlocking{  // Corrutina que añade un usuario
    launch {
        val id = MainActivity.database.UsuarioDao().addUsuario(usuario)   // Inserta un usuario nuevo

    }
}

