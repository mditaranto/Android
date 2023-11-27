@file:Suppress("DEPRECATION")

package com.example.examenvacas

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.examenvacas.Entidades.tConfiguracion
import com.example.examenvacas.Entidades.tMilkEntity
import com.example.examenvacas.Entidades.tVacaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import java.lang.Math.random
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Configuracion(navController: NavController) {
    TopAppBar(
        title = { Text(text = "ROOMY & DO Co.") },
        actions = {
            IconButton(onClick = { navController.navigate("Vacas") }) {
                Icon(
                    painter = painterResource(id = R.drawable.biberon),
                    contentDescription = "Ir a vacas"
                )
            }
            IconButton(onClick = { navController.navigate("Configuracion") }) {
                Icon(painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Configuracion")
            }


        }
    )

    Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Numero de vacas actuales: ${tConfiguracion.numVacas}")
        Text("Litros de leche por vaca: ${tConfiguracion.numLitros}")

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vacas(navController: NavController) {

    TopAppBar(
        title = { Text(text = "ROOMY & DO Co.") },
        actions = {
            IconButton(onClick = { updateTime(); navController.navigate("Vacas")}) {
                Icon (
                    painter = painterResource(id = R.drawable.tiempo),
                    contentDescription = "Modificar tiempo"
                )
            }
            IconButton(onClick = { navController.navigate("Vacas") }) {
                Icon(
                    painter = painterResource(id = R.drawable.biberon),
                    contentDescription = "Ir a vacas"
                )
            }
            IconButton(onClick = { navController.navigate("Configuracion") }) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Configuracion"
                )
            }
        })


    val sdf = SimpleDateFormat("dd/M/yyyy")
    val currentDate = sdf.format(Date())

    var lista by remember { mutableStateOf<List<tVacaEntity>>(emptyList()) }

    //Con una Corroutina damos valor a la variable usuarios
    LaunchedEffect(Unit) {
        val vacas = withContext(Dispatchers.IO) {
            //Get de los usuarios
            MainActivity.database2.VacasDao().getAllVacas()
        }
        vacas.sortBy { it.idVaca }
        for (i in 1..tConfiguracion.numVacas) {
            lista += (vacas[i-1])
        }
    }
    if (lista.size < tConfiguracion.numVacas) {
        val diferencia = tConfiguracion.numVacas - lista.size
        for (i in 1..diferencia) {
            addVaca(tVacaEntity(idVaca = 0, litrosActuales = 0))
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(lista.size) { vaca ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = lista[vaca].idVaca.toString())
                    Text(text = lista[vaca].litrosActuales.toString())
                    Button(onClick = { addMilk(tMilkEntity(fecha = currentDate,
                        idVaca = lista[vaca].idVaca, litrosExtraidos = lista[vaca].litrosActuales.toInt()));
                    ordeñarVaca(lista[vaca].idVaca); navController.navigate("Vacas")},
                        if (lista[vaca].litrosActuales >= tConfiguracion.numLitros*0.8) { Modifier.background(color = Color.Red)} else {
                        Modifier.background(color = Color.Green)
                    }) {
                            Text(text = "Ordeñar")
                    }
                }
            }

        }
    }

fun addMilk(milk: tMilkEntity) =
    runBlocking {  // Corrutina que añade una tarea a la lista
        launch {
            MainActivity.database2.VacasDao()
                .addMilk(milk)   // Inserta una tarea nueva
        }
    }

fun updateTime() =
    runBlocking {  // Corrutina que añade una tarea a la lista
        launch {
            val lista = MainActivity.database2.VacasDao().getAllVacas()

            for (vaca in lista) {
                val cantidad = (2..8).random()
                if (vaca.litrosActuales + cantidad > tConfiguracion.numLitros) {
                    MainActivity.database2.VacasDao().updateVaca(vaca.idVaca, tConfiguracion.numLitros - vaca.litrosActuales)
                } else
                    MainActivity.database2.VacasDao().updateVaca(vaca.idVaca, cantidad)
            }
        }
    }

fun ordeñarVaca(id: Long) =
    runBlocking {  // Corrutina que añade una tarea a la lista
        launch {
            MainActivity.database2.VacasDao().vacaOrdeñada(id)
        }
    }

fun addVaca(vaca: tVacaEntity) =
    runBlocking {  // Corrutina que añade una tarea a la lista
        launch {
            MainActivity.database2.VacasDao()
                .addVaca(vaca)   // Inserta una tarea nueva
        }
    }
