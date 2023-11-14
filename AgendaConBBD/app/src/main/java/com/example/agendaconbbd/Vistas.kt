package com.example.agendaconbbd

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agendaconbbd.Entity.ContactosEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Vista de los contactos
 * Tiene un boton para añadir un contacto
 * Tiene un boton para modificar un contacto
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaContactos(navController: NavController) {
    //Variable que guarda el get de los usuarios
    var lista by remember { mutableStateOf<List<ContactosEntity>>(emptyList()) }

    //Con una Corroutina damos valor a la variable usuarios
    LaunchedEffect(Unit) {
        val contactos = withContext(Dispatchers.IO) {
            //Get de los usuarios
            MainActivity.database.ContactosDao().getAllContactos()
        }
        lista = contactos
    }

    Scaffold (Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {navController.navigate("AñadirContacto")},
                containerColor = Color.Transparent,
                contentColor = Color.Black,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 5.dp, pressedElevation = 0.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }){

        Column {
            Row (
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                horizontalArrangement = Arrangement.Center){
                Text("Contactos", fontSize = 50.sp)
                }
            LazyColumn {
                for (a in lista)
                    items(1) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row (Modifier.weight(1F)){
                                Image(
                                    painter = painterResource(fotoContacto(a)), contentDescription = "",
                                    Modifier
                                        .width(90.dp)
                                        .height(90.dp)

                                )

                                Column {
                                    Row {
                                        Text(a.name, fontSize = 30.sp)
                                    }
                                    Row {
                                        Text(a.tlfno, fontSize = 20.sp)
                                    }
                                }
                            }

                            Spacer(Modifier.size(30.dp))

                            Row(horizontalArrangement = Arrangement.End) {

                                Image(
                                    painter = painterResource(id = R.drawable.editar),
                                    contentDescription = "editar",
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                        .clickable { navController.navigate("editarContacto/${a.id}") }
                                )


                                Spacer(Modifier.size(10.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.eliminar),
                                    contentDescription = "borrar",
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                        .clickable { deleteContacto(a); navController.navigate("ListaContactos") }
                                )

                            }

                            Spacer(Modifier.size(30.dp))
                        }
                    }
            }


        }
    }

}

/**
 * Vista de añadir un contacto
 * Boton para volver atras
 * Boton para añadir
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AñadirContacto(navController: NavController) {
    var nombre by remember {
        mutableStateOf("")
    }

    var telef by remember {
        mutableStateOf("")
    }

    var genero by remember {
        mutableStateOf(true)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, end = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cerrar),
                    contentDescription = "Cerrar",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clickable { navController.navigate("ListaContactos") }
                )
            }

                Text(
                    text = "Crear contacto",
                    fontSize = 25.sp
                )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, start = 5.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Image(painter = painterResource(id = R.drawable.guardar), contentDescription = "guardar",
                    Modifier.padding(end= 5.dp)
                        .height(60.dp)
                        .width(60.dp)
                        .clickable { addContacto(ContactosEntity(name = nombre, tlfno = telef, generoMasc = genero))
                        navController.navigate("ListaContactos")})
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            OutlinedTextField(value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Usuario") })
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(value = telef,
                onValueChange = { telef = it },
                label = { Text("Telefono") })
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Mujer")
                Switch(
                    checked = genero,
                    onCheckedChange = {
                        genero = it
                    },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text("Hombre")
            }
        }

    }


}

/**
 * Vista que edita un contacto
 * Boton para volver atras
 * Boton para guardar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarContacto(navController: NavController, id: Long) {

    var nombre by remember {
        mutableStateOf("")
    }

    var telef by remember {
        mutableStateOf("")
    }

    var genero by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        val contac = withContext(Dispatchers.IO) {
            MainActivity.database.ContactosDao()
                .getContactosById(id)    // Se carga la lista de tareas
        }
        nombre = contac.name
        telef = contac.tlfno
        genero = contac.generoMasc
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, end = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cerrar),
                    contentDescription = "Cerrar",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clickable { navController.navigate("ListaContactos") }
                )
            }

            Text(
                text = "Editar contacto",
                fontSize = 25.sp
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, start = 5.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Image(painter = painterResource(id = R.drawable.guardar), contentDescription = "guardar",
                    Modifier.padding(end= 5.dp)
                        .height(60.dp)
                        .width(60.dp)
                        .clickable { updateContacto(ContactosEntity(id.toInt(),name = nombre, tlfno = telef, generoMasc = genero))
                            navController.navigate("ListaContactos")})
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            OutlinedTextField(value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Usuario") })
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(value = telef,
                onValueChange = { telef = it },
                label = { Text("Telefono") })
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Mujer")
                Switch(
                    checked = genero,
                    onCheckedChange = {
                        genero = it
                    },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text("Hombre")
            }
        }

    }

}

/*
fun clearFocus(){
	findViewById<EditText>(R.id.etTask).setText("") // Borra el texto en el EditText
}

fun Context.hideKeyboard() {    // Oculta el teclado de texto
	val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}


 */


fun addContacto(contac: ContactosEntity) =
    runBlocking {  // Corrutina que añade una tarea a la lista
        launch {
            MainActivity.database.ContactosDao()
                .addContactos(contac)   // Inserta una tarea nueva
        }
    }

fun updateContacto(contac: ContactosEntity) = runBlocking {
    launch {
        MainActivity.database.ContactosDao()
            .updateContactos(contac) // Actualiza en la base de datos
    }
}

fun deleteContacto(contac: ContactosEntity) = runBlocking {
    launch {
        MainActivity.database.ContactosDao()
            .deleteContactos(contac) // ... y la borra de la base de datos.
    }
}


fun fotoContacto(contac: ContactosEntity): Int {

    val imagen = if (contac.generoMasc) {
        R.drawable.hombre
    } else {
        R.drawable.mujer
    }
    return imagen
}