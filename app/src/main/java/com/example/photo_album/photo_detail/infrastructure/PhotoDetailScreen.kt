package com.example.photo_album.photo_detail.infrastructure

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.photo_album.album_list.application.AlbumsViewModel
import com.example.photo_album.photo_detail.domain.PhotoModel
import com.example.photo_album.utils.infrastructure.MainTopAppBar

@Composable
fun PhotoDetailScreen(albumName: String, photoName: String, viewModel: AlbumsViewModel) {
    Scaffold(
        topBar = { MainTopAppBar(title = photoName) },
        content = { ScreenContent(albumName = albumName, photoName = photoName, viewModel = viewModel) }
    )
}

@Composable
private fun ScreenContent(albumName: String, photoName: String, viewModel: AlbumsViewModel){
    val photo = viewModel.state.value
        .albums.first{ album -> albumName == album.name }
        .photos.first { photo -> photoName == photo.name }

    if (viewModel.state.value.isLoading) {
        Log.d("PhotoDetailScreen", "this shouldn't happen")
    }

    DisplayPhotoDetails(photo = photo)
}
@Composable
private fun DisplayPhotoDetails(photo: PhotoModel) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Text(text = "Descripción : ${photo.description}")
            Text(text = "URL : ${photo.photo_url}")
            Text(text = "Creado : ${photo.dateCreated}")
        }
    }
}