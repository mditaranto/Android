package com.example.agendajet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agendajet.ui.theme.AgendaJetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ItemList(
                itemContacto = listOf(
                    Contacto("Mario","128736099", true),
                    Contacto("David","128730609", true),
                    Contacto("Lucia","128738609", false),
                    Contacto("Mateo","121873609", true),
                    Contacto("Marcos","128736209", true),
                    Contacto("Luis","128733609", true),
                    Contacto("Juan","128730609", true),
                    Contacto("Paola","128737609", true),
                    Contacto("Esteban","612873609", true),
                    Contacto("Marta","128731609", false),
                    Contacto("Lucas","128732609", true),
                    Contacto("Juan","128743609", true),
                    Contacto("Maria","123458821", false),
                )
            )

            }
        }

    @Composable
    fun ItemList(itemContacto: List<Contacto>) {
        LazyColumn{
            items(itemContacto) {
                itemContacto -> ContactoView(contacto = itemContacto)
            }
        }
    }

    @Composable
    fun ContactoView(contacto: Contacto) {
        Card(Modifier.fillMaxWidth()) {
            Row {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Foto contacto",
                        Modifier.height((100.dp))
                    )
                }

                Column {
                    Text(
                        text = contacto.nombre,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = contacto.telefono,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
    }


