package com.example.recycle

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.recycle.databinding.ContactosBinding
import com.example.recycle.databinding.ItemContactoBinding

class MainActivity : AppCompatActivity() {

    private lateinit var initials: TextView
    private lateinit var fullName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contactos = ContactosBinding.inflate(layoutInflater)
        setContentView(contactos.root)




        contactos.vistaContactos.adapter = ContactosAdapter(
            listOf(
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),
                Contacto("Juan","12873609"),


            ),object : ContactoPulsadoListener{
                override fun contactoPulsado(contacto: Contacto) {
                    val dial = Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:"+contacto.telefono)
                    ) // Creamos una llamada al Intent de llamadas
                    startActivity(dial)
                }
            }

        )

    }

}