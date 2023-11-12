package com.example.agendaconbbd

import android.app.Activity
import android.app.ZygotePreload
import android.content.Context
import android.media.Image
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column {
        Row {
            Button(onClick = { navController.navigate("AñadirContacto") }) {
                Text(text = "Añadir contacto")
            }
        }
        LazyColumn() {
            for (a in lista)
                items(1) {
                Row (verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Image(painter = painterResource(FotoContacto(a)), contentDescription = "",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp))
                    Text(a.name + " " + a.tlfno)
                    Spacer(modifier = Modifier.size(30.dp))
                    Button(onClick = { navController.navigate("editarContacto/${a.id.toLong()}") }, modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)) {
                        Image(painter = painterResource(id = R.drawable.editar),
                            contentDescription = "editar")
                    }


                    Spacer(modifier = Modifier.size(30.dp))
                    Image(painter = painterResource(id = R.drawable.eliminar),
                        contentDescription = "borrar",
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp))

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

    Column (modifier = Modifier.fillMaxSize()){

        Row (verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center){
            Button(onClick = { navController.navigate("ListaContactos") }, modifier = Modifier
                .width(50.dp)
                .height(50.dp)) {
                Image(painter = painterResource(id = R.drawable.mujer), contentDescription = "Cerrar")
            }
            Text(text = "Crear contacto", fontSize = 25.sp, modifier = Modifier.padding(35.dp,5.dp,35.dp,0.dp))
            Button(onClick = { addContacto(ContactosEntity(name = nombre, tlfno = telef, generoMasc = genero ))}, ) {
                Text(text = "Guardar")
            }
        }

        Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){

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
fun editarContacto(navController: NavController, id: Long) {

    var contacto by remember {
        mutableStateOf(ContactosEntity(1,"a","a",true))
    }
    LaunchedEffect(Unit) {
        val contac = withContext(Dispatchers.IO) {
           MainActivity.database.ContactosDao().getContactosById(id)    // Se carga la lista de tareas
        }
        contacto.id = contac.id
        contacto.name = contac.name
        contacto.tlfno = contac.tlfno
        contacto.generoMasc = contac.generoMasc
        // se pasa la lista a la Vista
    }

    var nombre by remember {
        mutableStateOf(contacto.name)
    }

    var telef by remember {
        mutableStateOf(contacto.tlfno)
    }

    var genero by remember {
        mutableStateOf(contacto.generoMasc)
    }

    Column (modifier = Modifier.fillMaxSize()){

        Row (verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center){
            Button(onClick = { navController.navigate("ListaContactos") }, modifier = Modifier
                .width(50.dp)
                .height(50.dp)) {
                Image(painter = painterResource(id = R.drawable.cerrar), contentDescription = "Cerrar")
            }
            Text(text = "Editar contacto", fontSize = 25.sp, modifier = Modifier.padding(35.dp,5.dp,35.dp,0.dp))
            Button(onClick = { updateContacto(ContactosEntity(id = id.toInt(), name = nombre,
                tlfno = telef, generoMasc = genero )) }, ) {
                Text(text = "Guardar")
            }
        }

        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()){

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


fun addContacto(contac: ContactosEntity)= runBlocking{  // Corrutina que añade una tarea a la lista
    launch {
        val id = MainActivity.database.ContactosDao().addContactos(contac)   // Inserta una tarea nueva
        //clearFocus()        // Se elimina el texto del et ...
        //hideKeyboard()      // y se oculta el teclado
    }
}

fun updateContacto(contac: ContactosEntity) = runBlocking{
    launch {
        MainActivity.database.ContactosDao().updateContactos(contac) // Actualiza en la base de datos
    }
}
/*
fun deleteTask(task: TaskEntity)= runBlocking{
    launch {
        val position = tasks.indexOf(task)  // Busca la posición de la tarea en la lista...
        MisNotasApp.database.taskDao().deleteTask(task) // ... y la borra de la base de datos.
        tasks.remove(task)      // Finalmente, la elimina de la lista
        adapter.notifyItemRemoved(position) // El adaptador notifica que se ha eliminado la tarea
    }
}
 */

fun FotoContacto(contac: ContactosEntity): Int {

    var imagen = 0
    if (contac.generoMasc) {
        imagen = R.drawable.hombre
    } else {
        imagen = R.drawable.mujer
    }
    return imagen
}