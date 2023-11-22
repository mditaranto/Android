package com.example.soniditos

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.soniditos.ui.theme.SoniditosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoniditosTheme {
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
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Animalitos", color = Color.White) }) },
        content = { MyContent() }
    )
}

// Creating a composable function to
// create two icon buttons namely play and pause
// Calling this function as content in the above function
@Composable
fun MyContent() {
    val mContext = LocalContext.current

    val gato = MediaPlayer.create(mContext, R.raw.gato)
    val perro = MediaPlayer.create(mContext, R.raw.perro)
    val vaca = MediaPlayer.create(mContext, R.raw.vaca)
    val ballena = MediaPlayer.create(mContext, R.raw.ballena)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageWithClick(mediaPlayer = gato, imageRes = R.drawable.gato)
            ImageWithClick(mediaPlayer = perro, imageRes = R.drawable.perro)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageWithClick(mediaPlayer = vaca, imageRes = R.drawable.vaca)
            ImageWithClick(mediaPlayer = ballena, imageRes = R.drawable.ballena)
        }
    }
}

@Composable
fun ImageWithClick(mediaPlayer: MediaPlayer, imageRes: Int) {
    val mContext = LocalContext.current
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .aspectRatio(1f)
            .clickable {
                mediaPlayer.start()
            },
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoniditosTheme {
        Greeting("Android")
    }
}