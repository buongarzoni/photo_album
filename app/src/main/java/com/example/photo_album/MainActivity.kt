package com.example.photo_album

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import com.example.photo_album.navigation.infrastructure.RootNavigationGraph
import com.example.photo_album.ui.theme.Photo_albumTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@AndroidEntryPoint
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

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun StartNavigation(){
    val navController = rememberAnimatedNavController()
    RootNavigationGraph(navController = navController)
}