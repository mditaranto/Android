package com.example.ejemplonosequebien

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplonosequebien.entidades.TaskEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TasksAdapter
    lateinit var tasks: MutableList<TaskEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tasks = ArrayList()         // Se prepara la lista
        getTasks()                  // Se carga la lista de tareas a través del DAO
        findViewById<Button>(R.id.btnAddTask).setOnClickListener {
            addTask(TaskEntity(name = findViewById<EditText>(R.id.etTask).text.toString()))}
    }

    fun clearFocus(){
        findViewById<EditText>(R.id.etTask).setText("") // Borra el texto en el EditText
    }

    fun Context.hideKeyboard() {    // Oculta el teclado de texto
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun getTasks()= runBlocking {       // Corrutina que saca de la base de datos la lista de tareas
        launch {                        // Inicio del hilo
            tasks = MisNotasApp.database.taskDao().getAllTasks()    // Se carga la lista de tareas
            setUpRecyclerView(tasks)        // se pasa la lista a la Vista
        }
    }

    fun addTask(task: TaskEntity)= runBlocking{  // Corrutina que añade una tarea a la lista
        launch {
            val id = MisNotasApp.database.taskDao().addTask(task)   // Inserta una tarea nueva
            val recoveryTask = MisNotasApp.database.taskDao().getTaskById(id)   // Recarga la lista
            tasks.add(recoveryTask) // Añade al final de la lista, el nuevo
            adapter.notifyItemInserted(tasks.size)  // El adaptador notifica que se ha insertado
            clearFocus()        // Se elimina el texto del et ...
            hideKeyboard()      // y se oculta el teclado
        }
    }

    fun updateTask(task: TaskEntity) = runBlocking{
        launch {
            task.isDone = !task.isDone  // Marca o desmarca el checkbox
            MisNotasApp.database.taskDao().updateTask(task) // Actualiza en la base de datos
        }
    }

    fun deleteTask(task: TaskEntity)= runBlocking{
        launch {
            val position = tasks.indexOf(task)  // Busca la posición de la tarea en la lista...
            MisNotasApp.database.taskDao().deleteTask(task) // ... y la borra de la base de datos.
            tasks.remove(task)      // Finalmente, la elimina de la lista
            adapter.notifyItemRemoved(position) // El adaptador notifica que se ha eliminado la tarea
        }
    }

    fun setUpRecyclerView(tasks: List<TaskEntity>) {    // Método que muestra la vista usando el adaptador
        adapter = TasksAdapter(tasks, { updateTask(it) }, {deleteTask(it)})
        recyclerView = findViewById(R.id.rvTask)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}