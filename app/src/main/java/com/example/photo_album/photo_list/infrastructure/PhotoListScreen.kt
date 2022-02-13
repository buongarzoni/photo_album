package com.example.photo_album.photo_list.infrastructure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.photo_album.navigation.domain.AlbumRoutes
import com.example.photo_album.utils.infrastructure.MainTopAppBar

@Composable
fun PhotoListScreen(navController: NavHostController, albumName: String) {
    Scaffold(
        topBar = { MainTopAppBar(title = albumName) },
        content = { ScreenContent(navController = navController) }
    )
}

@Composable
private fun ScreenContent(navController: NavHostController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Button(onClick = { navController.navigate(AlbumRoutes.PhotoDetail.route) }) {
                Text(text = "Navegar al detalle de la foto")
            }
        }
    }
}