package com.example.a7ymedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.a7ymedia.databinding.ActivityMainBinding
import com.example.a7ymedia.databinding.JueguitoBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var horizontalScrollView: HorizontalScrollView
    private lateinit var horizontalScrollViewj2: HorizontalScrollView
    private lateinit var binding: ActivityMainBinding
    private lateinit var jueguitoBinding: JueguitoBinding
    private var puntosj1: Float = 0F
    private var puntosj2: Float = 0F
    private var terminadoj1: Boolean = false
    private var terminadoj2: Boolean = false

    private lateinit var j1: String
    private lateinit var j2: String

    private val random = Random()
    private lateinit var foto: ImageView

    private val imagenes = arrayOf(
        R.drawable.carta1,
        R.drawable.carta2,
        R.drawable.carta3,
        R.drawable.carta4,
        R.drawable.carta5,
        R.drawable.carta6,
        R.drawable.carta7,
        R.drawable.carta10,
        R.drawable.carta11,
        R.drawable.carta12

    )

    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayoutj2: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        jueguitoBinding = JueguitoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        horizontalScrollView = jueguitoBinding.horizontal
        horizontalScrollViewj2 = jueguitoBinding.horizontal2


        binding.jugar.setOnClickListener {
            j1 = binding.j1.text.toString()
            j2 = binding.j2.text.toString()
            jueguitoBinding.j1nomb.text = j1
            jueguitoBinding.j2nomb.text = j2
            foto = ImageView(this)

            setContentView(jueguitoBinding.root)
        }

        linearLayout = jueguitoBinding.fotitos
        linearLayoutj2 = jueguitoBinding.fotitosj2

        jueguitoBinding.cartaj1.setOnClickListener {
            if (!terminadoj1)
            agregarImagenj1()
            jueguitoBinding.j1punt.text = puntosj1.toString()
        }

        jueguitoBinding.cartaj2.setOnClickListener {
            if (!terminadoj2)
            agregarImagenj2()
            jueguitoBinding.j2punt.text = puntosj2.toString()
        }

        jueguitoBinding.plantarsej1.setOnClickListener {
            terminadoj1 = true
        }

        jueguitoBinding.plantarsej2.setOnClickListener {
            terminadoj2 = true
        }

        if (terminadoj1 && terminadoj2) {

        }



    }

    private fun agregarImagenj1() {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Crear un nuevo ImageView para cada imagen
        val imageView = ImageView(this)
        imageView.layoutParams = layoutParams

        val indiceAleatorio = random.nextInt(imagenes.size)
        val imagenSeleccionada = imagenes[indiceAleatorio]
        imageView.setImageResource(imagenSeleccionada)

        if (indiceAleatorio <= 6) {
            puntosj1 += indiceAleatorio + 1
        } else {
            puntosj1 += 0.5F
        }

        terminarj1()

        // Agregar el nuevo ImageView al LinearLayout
        linearLayout.addView(imageView)

        val tamaño = linearLayout.width
        horizontalScrollView.post {
            horizontalScrollView.scrollTo(tamaño, 0)
        }
        jueguitoBinding.j1punt.text = puntosj1.toString()
    }

    private fun agregarImagenj2() {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Crear un nuevo ImageView para cada imagen
        val imageView = ImageView(this)
        imageView.layoutParams = layoutParams

        val indiceAleatorio = random.nextInt(imagenes.size)
        val imagenSeleccionada = imagenes[indiceAleatorio]
        imageView.setImageResource(imagenSeleccionada)

        if (indiceAleatorio <= 6) {
            puntosj2 += indiceAleatorio + 1
        } else {
            puntosj2 += 0.5F
        }

        terminarj2()

        // Agregar el nuevo ImageView al LinearLayout
        linearLayoutj2.addView(imageView)

        val tamaño = linearLayoutj2.width
        horizontalScrollViewj2.post {
            horizontalScrollViewj2.scrollTo(tamaño, 0)
        }
    }

    private fun terminarj1 () {
        if (puntosj1 >= 7.5) {
            terminadoj1 = true
        }
    }

    private fun terminarj2 () {
        if (puntosj2 >= 7.5) {
            terminadoj2 = true
        }
    }


}
