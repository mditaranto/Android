package com.example.miprimeraapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.miprimeraapp.databinding.BienvenidaBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout: View
    private lateinit var pauseLayout: View

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = BienvenidaBinding.inflate(layoutInflater)

        // Inflar las vistas principal y de pausa
        mainLayout = layoutInflater.inflate(R.layout.activity_main, null)
        pauseLayout = layoutInflater.inflate(R.layout.pausa, null)

        // Configurar la vista principal en el inicio
        setContentView(mainLayout)

        val boton = findViewById<Button>(R.id.botonn)

        boton.setOnClickListener {
            val nombreEditText = findViewById<EditText>(R.id.user)
            val nombre = nombreEditText.text.toString()
            binding.textobien.text = "Bienvenido $nombre"
            setContentView(binding.root)
        }


    }

    override fun onResume() {
        super.onResume()
        // Cuando la aplicación se reanuda, vuelve a la vista principal
        val toast = Toast.makeText(
            applicationContext, "Bienvenido de nuevo",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPause() {
        super.onPause()
        // Cuando la aplicación entra en pausa, muestra la vista de pausa
        setContentView(pauseLayout)
    }

    override fun onDestroy() {
        super.onDestroy()
        var builder = NotificationCompat.Builder(this)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }


}