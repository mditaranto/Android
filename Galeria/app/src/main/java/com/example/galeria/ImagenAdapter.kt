package com.example.galeria

import androidx.recyclerview.widget.RecyclerView

class ImagenAdapter(private val imagenes: List<Imagen>,
private val imagenPulsadaListener: ImagenPulsadaListener)
: RecyclerView.Adapter<ImagenAdapter.ViewHolder>() {
}