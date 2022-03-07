package com.example.photo_album.photo_detail.infrastructure

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.photo_album.R
import com.example.photo_album.album_list.application.AlbumsViewModel
import com.example.photo_album.photo_detail.domain.PhotoModel
import com.example.photo_album.utils.infrastructure.MainTopAppBar
import com.example.photo_album.utils.infrastructure.animatedShimmerBrush

@ExperimentalCoilApi
@Composable
fun PhotoDetailScreen(albumName: String, photoName: String, viewModel: AlbumsViewModel) {
    Scaffold(
        topBar = { MainTopAppBar(title = photoName) },
        content = { ScreenContent(albumName = albumName, photoName = photoName, viewModel = viewModel) }
    )
}

@ExperimentalCoilApi
@Composable
private fun ScreenContent(albumName: String, photoName: String, viewModel: AlbumsViewModel){
    val photo = viewModel.state.value
        .albums.first{ album -> albumName == album.name }
        .photos.first { photo -> photoName == photo.name }

    DisplayPhotoDetails(photo = photo)
}

@ExperimentalCoilApi
@Composable
private fun DisplayPhotoDetails(photo: PhotoModel) {
    val mustShowDetails = remember{ mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {
        AddImage(photoUrl = photo.photo_url, name = photo.name,){
            mustShowDetails.value = !mustShowDetails.value
        }
        ShowDetails(photo = photo, mustShowDetails = mustShowDetails.value)
    }
}

@ExperimentalCoilApi
@Composable
private fun AddImage(photoUrl: String, name: String, imageClicked: () -> Unit) {
    val painter = rememberImagePainter(data = photoUrl)

    if (painter.state is ImagePainter.State.Loading) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(5))
                .background(animatedShimmerBrush())
        )
    }

    if (painter.state is ImagePainter.State.Error) {
        Image(
            painter = painterResource(id = R.drawable.image_not_found_placeholder),
            contentDescription = stringResource(id = R.string.content_description_image_not_found_placeholder),
            modifier = Modifier.fillMaxSize()
        )
    }

    Image(
        painter = painter,
        contentDescription = name,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                imageClicked()
            },
        alignment = Alignment.Center
    )
}

@Composable
private fun ShowDetails(photo: PhotoModel, mustShowDetails: Boolean) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    AnimatedVisibility (
        visible = mustShowDetails,
        enter = enterAnimatedTransition(screenHeight = screenHeight),
        exit = exitAnimatedTransition(screenHeight = screenHeight)
    ) {
        AddGradient()
        AddDetails(photo = photo)
    }
}

@Composable
private fun AddGradient() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black)))
    )
}

@Composable
private fun AddDetails(photo: PhotoModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {

            Text(
                text = stringResource(id = R.string.photo_detail_screen_description),
                style = styleForTextOverGradient()
            )
            Text(
                text = photo.description,
                style = styleForTextOverGradient()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = "${stringResource(id = R.string.photo_detail_screen_date_created)} : ${photo.dateCreated}",
                    style = styleForTextOverGradient()
                )
            }
        }

    }
}

@Composable
private fun enterAnimatedTransition(screenHeight: Int): EnterTransition {
    return slideIn(
        animationSpec = tween(500),
        initialOffset = { IntOffset(0, screenHeight*2) }
    )
}

@Composable
private fun exitAnimatedTransition(screenHeight: Int): ExitTransition {
    return slideOut(
        animationSpec = tween(500),
        targetOffset = { IntOffset(0, screenHeight*2) }
    )
}

@Composable
private fun styleForTextOverGradient(): TextStyle{
    return TextStyle(
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal
    )
}
