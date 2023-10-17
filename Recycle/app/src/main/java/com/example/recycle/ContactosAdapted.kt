package com.example.recycle
import android.content.ClipData
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.recycle.databinding.ItemContactoBinding

class ContactosAdapter (private val contactos : List<Contacto>,
                        private val contactoPulsadoListener:ContactoPulsadoListener): RecyclerView.Adapter<ContactosAdapter.ViewHolder>() {

    private lateinit var initials: LinearLayout
    private lateinit var fullName: LinearLayout
    private lateinit var foto: ImageView

    class ViewHolder(private val binding : ItemContactoBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind ( contacto:Contacto) {
            binding.nombre.text = contacto.nombre
            binding.telefono.text = contacto.telefono
        }
    }


    private fun toggleFullName() {
        if (fullName.visibility == View.GONE) {
            fullName.visibility = View.VISIBLE
            initials.visibility = View.GONE

        } else {
            fullName.visibility = View.GONE
            initials.visibility = View.VISIBLE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        initials = binding.inic
        fullName = binding.sinInic
        foto = binding.perfil

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contactos[position])

        holder.itemView.setOnClickListener{
            contactoPulsadoListener.contactoPulsado(contactos[position])
        }

        foto.setOnClickListener {
            toggleFullName()
        }

    }

    override fun getItemCount(): Int {
            return contactos.size
    }


}






