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
import com.bumptech.glide.Glide
import com.example.recycle.databinding.ItemContactoBinding
import java.util.Locale

class ContactosAdapter (private val contactos : List<Contacto>,
                        private val contactoPulsadoListener:ContactoPulsadoListener): RecyclerView.Adapter<ContactosAdapter.ViewHolder>() {

    private lateinit var nombre: TextView
    class ViewHolder(val binding : ItemContactoBinding):RecyclerView.ViewHolder(binding.root) {

            fun bind(contacto: Contacto) {

                val imagen = binding.perfil

                Glide
                    .with(binding.root.context)
                    .load("https://loremflickr.com/320/240")
                    .into(imagen)

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

        val imageView = holder.binding.perfil

        holder.itemView.setOnClickListener {


    }




}}






