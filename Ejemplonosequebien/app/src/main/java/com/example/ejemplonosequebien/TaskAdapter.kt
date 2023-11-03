package com.example.ejemplonosequebien

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplonosequebien.entidades.TaskEntity

class TasksAdapter(
    val tasks: List<TaskEntity>,                   // Objeto Lista de tareas
    val checkTask: (TaskEntity) -> Unit,            // chequeo de tarea
    val deleteTask: (TaskEntity) -> Unit            // borrado de tarea
) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {    // Devuelve la vista

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {     // Muestra la vista (holder) y cada tarea de la lista (position)
        val item = tasks[position]                                         // Extrae la tarea de la lista
        holder.bind(item, checkTask, deleteTask)                           // Muestra el item en la vista (ver más adelante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {    // Contenedor de la vista (holder) y la posición de la tarea en la lista (position)
        val layoutInflater = LayoutInflater.from(parent.context)                       // Se instancia el Layout para la vista
        return ViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))   // Devuelve la vista inflando el layout del item
    }

    override fun getItemCount(): Int {
        return tasks.size     // Devuelve el número de tareas de la lista
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {     // Clase con la vista
        val tvTask = view.findViewById<TextView>(R.id.tvTask)         // instancia del Textview de la vista
        val cbIsDone = view.findViewById<CheckBox>(R.id.cbIsDone)     // instancia del Checkbox de la vista

        fun bind(                                   // función que une los elementos en la vista y prepara los listeners
            task: TaskEntity,
            checkTask: (TaskEntity) -> Unit,
            deleteTask: (TaskEntity) -> Unit
        ) {
            tvTask.text = task.name
            cbIsDone.isChecked = task.isDone
            cbIsDone.setOnClickListener { checkTask(task) }
            itemView.setOnClickListener { deleteTask(task) }
        }
    }
}