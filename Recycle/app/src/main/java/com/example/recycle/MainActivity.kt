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
                Contacto("Mario","128736099", true),
                Contacto("David","128730609", true),
                Contacto("Lucia","128738609", false),
                Contacto("Mateo","121873609", true),
                Contacto("Marcos","128736209", true),
                Contacto("Luis","128733609", true),
                Contacto("Juan","128730609", true),
                Contacto("Paola","128737609", true),
                Contacto("Esteban","612873609", true),
                Contacto("Marta","128731609", false),
                Contacto("Lucas","128732609", true),
                Contacto("Juan","128743609", true),
                Contacto("Maria","123458821", false),


            ),object : ContactoPulsadoListener{
                override fun contactoPulsado(contacto: Contacto) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${contacto.telefono}")
                    startActivity(intent)
                }
            }

        )

    }

}