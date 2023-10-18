package com.example.galeria

import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galeria.databinding.FotosItemBinding

class ImagenAdapter(private val imagenes: List<Imagen>,
                    private val imagenPulsadaListener: ImagenPulsadaListener)
: RecyclerView.Adapter<ImagenAdapter.ViewHolder>() {
    class ViewHolder(val binding : FotosItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(imagen: Imagen) {

            Glide
                .with(binding.root.context)
                .load(R.drawable.fotoej1)
                .into(binding.fotitos)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FotosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imagenes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imagenes[position])

        // Ponemos un clickListener en la imagen
        holder.binding.fotitos.setOnClickListener {
            imagenPulsadaListener.imagenPulsada(imagenes[position])
        }
        val transition = TransitionInflater.from(holder.itemView.context).inflateTransition(android.R.transition.fade)

}}