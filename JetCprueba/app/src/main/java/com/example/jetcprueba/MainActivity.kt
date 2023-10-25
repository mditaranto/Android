package com.example.jetcprueba

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jetcprueba.ui.theme.JetCpruebaTheme
import org.jetbrains.annotations.Async

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCpruebaTheme {
                // A surface container using the 'background' color from the theme
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
    Row {

        Column {

            Text(
                text = "Hello $name!",
                modifier = modifier,
                color = Color(233, 27,159, 255)
            )

        }
        Column {

            Text(
                text = "Hello $name!",
                modifier = modifier
            )

        }

        /*AsyncImage (
            model = "https://img.freepik.com/foto-gratis/primer-disparo-flor-morada_181624-25863.jpg?size=626&ext=jpg&ga=GA1.1.386372595.1698019200&semt=sph",
            contentDescription = "Flor",
            modifier.size(400.dp,400.dp)
        )*/
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetCpruebaTheme {
        Greeting("Android")
    }
}}