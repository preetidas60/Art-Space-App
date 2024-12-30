package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    val artList = listOf(
        ArtPiece(R.drawable.pic1, "Wheat Field with Cypresses", "Van Gogh, Vincent (1853)"),
        ArtPiece(R.drawable.pic2, "Mona Lisa", "Da Vinci, Leonardo (1503)"),
        ArtPiece(R.drawable.pic3, "The Kiss, detail (Grey variation)", "Klimt, Gustav (1907)"),
        ArtPiece(R.drawable.pic4, "The Milkmaid", "Vermeer, Johannes (1661)"),
        ArtPiece(R.drawable.pic5, "Girl With A Pearl Earring", "Vermeer, Jantje (1665)"),
        ArtPiece(R.drawable.pic6, "The Last Supper", "Da Vinci, Leonardo (1495)"),
        ArtPiece(R.drawable.pic7, "Starry Night", "Van Gogh, Vincent (1889)"),
        ArtPiece(R.drawable.pic8, "The Artists Sister at a Window", "Morisot, Berthe (1869)"),
        ArtPiece(R.drawable.pic8, "The Artists Sister at a Window", "Morisot, Berthe (1869)")



    )

    var currentIndex by remember { mutableStateOf(0) }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Main content (Image and details)
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Reserve space for buttons
        ) {
            // Image display
            Card(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(artList[currentIndex].imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = (scale * zoom).coerceIn(1f, 5f) // Limit zoom levels
                                offset = Offset(
                                    x = offset.x + pan.x * scale,
                                    y = offset.y + pan.y * scale
                                )
                            }
                        },

                    contentScale = ContentScale.Crop, // Make the image fill the card
                    alignment = Alignment.Center
                )
            }

            // Title and Artist info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 40.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFFD3D2D3))
                    .padding(20.dp)
            ) {
                Text(
                    text = artList[currentIndex].title,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = artList[currentIndex].artist,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Navigation buttons at the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    currentIndex = if (currentIndex > 0) currentIndex - 1 else artList.size - 1
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFAF9F9),
                    containerColor = Color(0xFFC1AAEB)
                )
            ) {
                Text(text = "Previous", fontSize = 17.sp)
            }

            Button(
                onClick = {
                    currentIndex = if (currentIndex < artList.size - 1) currentIndex + 1 else 0
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFAF9F9),
                    containerColor = Color(0xFFC1AAEB)
                )
            ) {
                Text(text = "Next", fontSize = 17.sp)
            }
        }
    }
}


// Data class to hold art piece details
data class ArtPiece(val imageRes: Int, val title: String, val artist: String)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}
