package com.example.recycle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.recycle.databinding.ItemContactoBinding
import java.util.Locale

class ContactosAdapter (private val contactos : List<Contacto>,
                        private val contactoPulsadoListener:ContactoPulsadoListener): RecyclerView.Adapter<ContactosAdapter.ViewHolder>() {

    private lateinit var nombre: TextView
    class ViewHolder(val binding : ItemContactoBinding):RecyclerView.ViewHolder(binding.root) {

            fun bind(contacto: Contacto) {
                val nombres: List<String> = contacto.nombre.split(" ")

                var iniciales = ""
                if (nombres.size == 1) {
                    iniciales = nombres[0].substring(0, 2).uppercase(Locale.ROOT)
                } else {
                    for (nombre: String in nombres) {
                        iniciales += nombre[0]
                        iniciales.uppercase()
                    }
                }
                val imagen = binding.perfil
                if (contacto.hombre) {
                    imagen.setImageResource(R.drawable.perfil)
                } else {
                    imagen.setImageResource(R.drawable.mujer)
                }

                binding.textView.text = iniciales
                binding.nombre.text = contacto.nombre
                binding.telefono.text = contacto.telefono

            }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contactos[position])
        val transition = TransitionInflater.from(holder.itemView.context).inflateTransition(android.R.transition.fade)

        holder.itemView.setOnClickListener {

            val initials = holder.binding.textView
            nombre = holder.binding.nombre
            val telf = holder.binding.telefono

            TransitionManager.beginDelayedTransition(holder.itemView as ViewGroup?, transition)

            if (initials.visibility == View.GONE) {
                initials.visibility = View.VISIBLE
                nombre.visibility = View.GONE
                telf.visibility = View.GONE

            } else {
                initials.visibility = View.GONE
                nombre.visibility = View.VISIBLE
                telf.visibility = View.VISIBLE
            }
        }

        holder.binding.telefono.setOnClickListener() {
            contactoPulsadoListener.contactoPulsado(contactos[position])
        }

    }




}






