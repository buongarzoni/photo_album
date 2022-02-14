package com.example.photo_album.photo_list.infrastructure

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.photo_album.R
import com.example.photo_album.helpers.PhotosMock
import com.example.photo_album.navigation.domain.AlbumRoutes
import com.example.photo_album.photo_list.application.AlbumGalleryViewModel
import com.example.photo_album.photo_list.domain.Photo
import com.example.photo_album.utils.infrastructure.MainTopAppBar

@ExperimentalFoundationApi
@Composable
fun PhotoListScreen(navController: NavHostController, albumName: String, viewModel: AlbumGalleryViewModel) {
    Scaffold(
        topBar = { MainTopAppBar(title = albumName) },
        content = { ScreenContent(navController = navController, viewModel = viewModel) }
    )
}

@ExperimentalFoundationApi
@Composable
private fun ScreenContent(navController: NavHostController, viewModel: AlbumGalleryViewModel){
    if (viewModel.state.value.isLoading) {
        LoadingContent()
    }

    if (viewModel.state.value.photos.isNotEmpty()) {
        DisplayAlbumContent(photos = viewModel.state.value.photos, navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingContent(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.searching_album_content))
        Spacer(modifier = Modifier.padding(5.dp))
        CircularProgressIndicator()
    }
}

@ExperimentalFoundationApi
@Composable
private fun DisplayAlbumContent(photos: List<Photo>, navController: NavHostController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = photos){ photo ->
            PhotoCard(photo = photo, navController = navController)
        }
    }
}

@Composable
private fun PhotoCard(photo: Photo, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate(AlbumRoutes.PhotoDetail.passPhotoName(photoName = photo.name)) },
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .size(224.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.secondary)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true)
fun PreviewDisplayAlbumContent() {
    DisplayAlbumContent(photos = PhotosMock.fakePhotos, navController = rememberNavController())
}

@Composable
@Preview(showBackground = true)
fun PreviewPhotoCard() {
    PhotoCard(photo = PhotosMock.fakePhotos[0], navController = rememberNavController())
}