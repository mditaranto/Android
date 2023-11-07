package com.example.piedrapapelotijeras

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredName
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
import com.example.piedrapapelotijeras.ui.theme.PiedraPapelOTijerasTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PiedraPapelOTijerasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }

    @Composable
    fun GameScreen() {

        //Lista donde guardo las fotos que voy a utilizar
        val lista = listOf(
            R.drawable.ic_launcher_foreground,
            R.drawable.piedra,
            R.drawable.tijeras,
            R.drawable.papel
        )

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
                Toast.makeText(this,"Ronda empatada", Toast.LENGTH_SHORT).show()
            } else if (eleccionM == 2 && eleccionJ == 3) {
                Toast.makeText(this,"La maquina gana la ronda", Toast.LENGTH_SHORT).show()
                victoriasM += 1
            } else if (eleccionM == 3 && eleccionJ == 1) {
                Toast.makeText(this,"La maquina gana la ronda", Toast.LENGTH_SHORT).show()
                victoriasM += 1
            } else if (eleccionM == 1 && eleccionJ == 2) {
                Toast.makeText(this,"La maquina gana la ronda", Toast.LENGTH_SHORT).show()
                victoriasM += 1
            } else {
                Toast.makeText(this,"El jugador gana la ronda", Toast.LENGTH_SHORT).show()
                victoriasJ += 1
            }

        }

        //Cuando alguno de los dos llegue a 5, se informa el ganador
        if (victoriasJ == 5) {
            Toast.makeText(this,"Ha ganado el jugador", Toast.LENGTH_SHORT).show()
            victoriasJ = 0
            victoriasM = 0
            eleccionJ = 0
            eleccionM = 0
        } else if (victoriasM == 5) {
            Toast.makeText(this,"Ha ganado la maquina", Toast.LENGTH_SHORT).show()
            victoriasJ = 0
            victoriasM = 0
            eleccionJ = 0
            eleccionM = 0
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

        @Preview(showBackground = true)
        @Composable
        fun GreetingPreview() {
            PiedraPapelOTijerasTheme {
                GameScreen()
            }
        }



    }


