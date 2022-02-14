package com.example.photo_album.album_list.infrastructure

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.photo_album.R
import com.example.photo_album.album_list.application.AlbumsViewModel
import com.example.photo_album.album_list.domain.AlbumModel
import com.example.photo_album.helpers.AlbumsMock
import com.example.photo_album.navigation.domain.AlbumRoutes
import com.example.photo_album.utils.infrastructure.MainTopAppBar

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun AlbumListScreen(navController: NavHostController, viewModel: AlbumsViewModel) {
    Scaffold(
        topBar = { MainTopAppBar(title = stringResource(id = R.string.gallery_top_app_bar)) },
        content = { ScreenContent(navController = navController, viewModel = viewModel) }
    )
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
private fun ScreenContent(navController: NavHostController, viewModel: AlbumsViewModel){

    if (viewModel.state.value.isLoading) {
        LoadingContent()
    }

    if (viewModel.state.value.albums.isNotEmpty()) {
        DisplayAlbums(albums = viewModel.state.value.albums, navController = navController)
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
private fun DisplayAlbums(albums: List<AlbumModel>, navController: NavHostController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = albums){ album ->
            AlbumCard(album = album, navController = navController)
        }
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
        Text(text = stringResource(id = R.string.searching_albums))
        Spacer(modifier = Modifier.padding(5.dp))
        CircularProgressIndicator()
    }
}

@ExperimentalCoilApi
@Composable
private fun AlbumCard(album: AlbumModel, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate(AlbumRoutes.PhotoList.passAlbumName(album.name)) },
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddImage(photoUrl = album.photoUrl, name = album.name)

            Text(text = album.name, modifier = Modifier.padding(10.dp))
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun AddImage(photoUrl: String, name: String) {

    Box(modifier = Modifier
        .height(224.dp)
        .width(224.dp),
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberImagePainter(
            data = photoUrl,
            builder = {}
        )

        if (painter.state is ImagePainter.State.Loading) {
            CircularProgressIndicator()
        }

        Image(painter = painter, contentDescription = name)
    }
}


@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true)
fun PreviewDisplayAlbums() {
    DisplayAlbums(albums = AlbumsMock.fakeAlbums, navController = rememberNavController())
}

@ExperimentalCoilApi
@Composable
@Preview(showBackground = true)
fun PreviewAlbumCard() {
    AlbumCard(album = AlbumModel(name = "Título", photoUrl = "Random url", photos = emptyList()), navController = rememberNavController())
}
