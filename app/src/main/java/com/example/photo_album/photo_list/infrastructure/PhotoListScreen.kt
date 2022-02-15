package com.example.photo_album.photo_list.infrastructure

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.photo_album.R
import com.example.photo_album.album_list.application.AlbumsViewModel
import com.example.photo_album.helpers.PhotosMock
import com.example.photo_album.navigation.domain.AlbumRoutes
import com.example.photo_album.photo_detail.domain.PhotoModel
import com.example.photo_album.utils.infrastructure.MainTopAppBar
import com.example.photo_album.utils.infrastructure.animatedShimmerBrush

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun PhotoListScreen(navController: NavHostController, albumName: String, viewModel: AlbumsViewModel) {
    Scaffold(
        topBar = { MainTopAppBar(title = albumName) },
        content = { ScreenContent(navController = navController, albumName = albumName, viewModel = viewModel) }
    )
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
private fun ScreenContent(navController: NavHostController, albumName: String, viewModel: AlbumsViewModel){
    val photos = viewModel.state.value
        .albums.first{ album -> albumName == album.name }
        .photos

    if (viewModel.state.value.isLoading) {
        Log.d("PhotoListScreen", "this shouldn't happen")
        LoadingContent()
    }

    DisplayAlbumContent(photos = photos, albumName = albumName, navController = navController)
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

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
private fun DisplayAlbumContent(photos: List<PhotoModel>, albumName: String, navController: NavHostController) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = photos){ photo ->
            PhotoCard(photo = photo, albumName = albumName, navController = navController)
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun PhotoCard(photo: PhotoModel, albumName: String, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate(
                    AlbumRoutes.PhotoDetail.passPhotoName(
                        albumName = albumName,
                        photoName = photo.name
                    )
                )
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddImage(photoUrl = photo.photo_url, name = photo.name)
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
            Spacer(
                modifier = Modifier
                    .size(124.dp)
                    .clip(RoundedCornerShape(5))
                    .background(animatedShimmerBrush())
            )
        }

        Image(painter = painter, contentDescription = name)
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true)
fun PreviewDisplayAlbumContent() {
    DisplayAlbumContent(photos = PhotosMock.fakePhotos, albumName = "album 1", navController = rememberNavController())
}

@ExperimentalCoilApi
@Composable
@Preview(showBackground = true)
fun PreviewPhotoCard() {
    PhotoCard(photo = PhotosMock.fakePhotos[0], albumName = "album 1", navController = rememberNavController())
}
