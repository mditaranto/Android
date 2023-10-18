package com.example.galeria

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.galeria.databinding.FotosBinding

class MainActivity : AppCompatActivity() {

    val imagenList = ArrayList<Imagen>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vistaFotos.adapter = ImagenAdapter(
            listOf(
                Imagen(R.drawable.fotoej1),
            ),object: ImagenPulsadaListener {
                override fun imagenPulsada(imagen: Imagen) {

                }
            }
        )
    }
}