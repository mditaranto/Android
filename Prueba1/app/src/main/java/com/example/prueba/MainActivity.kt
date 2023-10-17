package com.example.prueba

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.prueba.databinding.OtraVistaBinding
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = OtraVistaBinding.inflate(layoutInflater)
        setContentView(R.layout.otra_vista)

        val texto = findViewById<TextView>(R.id.nombre)

        texto.text="Hola soy tu"

        val clickMeBtn = findViewById<TextView>(R.id.button2)
        setContentView(binding.root)
        binding.button2.text = "Pulsa aqui"
        binding.button2.setLinkTextColor(R.color.Si)

        binding.button2.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?) {
                val toast = Toast.makeText(
                    applicationContext, "Me has pulsado",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
        /*val PI: Double = 3.1416
        var numero: Int = 7
        var exponente: Int = 3

        Log.d("ETIQUETA", potencia(numero, exponente))
        var resultado: Double = PI + numero
        Log.d("Etiqueta", resultado.toString())
    }

    private fun potencia(numero:Int, exponente:Int): String{
        var resultado: Int = numero.toDouble().pow(exponente.toDouble()).toInt()
        return "El resultado de elevar $numero a $exponente es $resultado"
    */
    }

}