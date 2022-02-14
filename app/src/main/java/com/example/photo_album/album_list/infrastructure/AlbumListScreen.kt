package com.example.photo_album.album_list.infrastructure

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.photo_album.R
import com.example.photo_album.album_list.application.AlbumsViewModel
import com.example.photo_album.album_list.domain.Album
import com.example.photo_album.helpers.AlbumsMock
import com.example.photo_album.navigation.domain.AlbumRoutes
import com.example.photo_album.utils.infrastructure.MainTopAppBar

@ExperimentalFoundationApi
@Composable
fun AlbumListScreen(navController: NavHostController) {
    Scaffold(
        topBar = { MainTopAppBar(title = stringResource(id = R.string.gallery_top_app_bar)) },
        content = { ScreenContent(navController = navController) }
    )
}

@ExperimentalFoundationApi
@Composable
private fun ScreenContent(navController: NavHostController){
    val viewModel: AlbumsViewModel = hiltViewModel()

    if (viewModel.state.value.isLoading) {
        LoadingContent()
    }

    if (viewModel.state.value.albums.isNotEmpty()) {
        DisplayAlbums(albums = viewModel.state.value.albums, navController = navController)
    }
}

@ExperimentalFoundationApi
@Composable
private fun DisplayAlbums(albums: List<Album>, navController: NavHostController) {
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

@Composable
private fun AlbumCard(album: Album, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate(AlbumRoutes.PhotoList.passAlbumName(album.title)) },
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
            Text(text = album.title, modifier = Modifier.padding(10.dp))
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true)
fun PreviewDisplayAlbums() {
    DisplayAlbums(albums = AlbumsMock.fakeAlbums, navController = rememberNavController())
}

@Composable
@Preview(showBackground = true)
fun PreviewAlbumCard() {
    AlbumCard(album = Album(title = "Título", photo_url = "Random url"), navController = rememberNavController())
}
