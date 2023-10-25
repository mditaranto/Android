package com.example.iniciojc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iniciojc.ui.theme.InicioJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InicioJCTheme{
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
            Greeting("Android")

        }
        }
    }
}



    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column (
            Modifier
                .fillMaxSize()
        )
        {
            Image(painter = painterResource(id = (R.drawable.logonervion300)),
                contentDescription = "nervion",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(300.dp,300.dp)
                )

            Text(text = "usuario: ")

        }


        }





    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        InicioJCTheme {
            Greeting("Android")
        }
    }
}

          /*  val boton = findViewById<Button>(R.id.botonn)

            boton.setOnClickListener {
                val nombreEditText = findViewById<EditText>(R.id.user)
                val nombre = nombreEditText.text.toString()
                binding.textobien.text = "Bienvenido $nombre"
                setContentView(binding.root)
            }



        }


    }
} */