package com.example.photo_album

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.photo_album.navigation.infrastructure.RootNavigationGraph
import com.example.photo_album.ui.theme.Photo_albumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Photo_albumTheme {
                Surface(color = MaterialTheme.colors.background) {
                    StartNavigation()
                }
            }
        }
    }
}

@Composable
fun StartNavigation(){
    val navController = rememberNavController()
    RootNavigationGraph(navController = navController)
}