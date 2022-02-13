package com.example.photo_album.photo_detail.infrastructure

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
import androidx.compose.ui.res.stringResource
import com.example.photo_album.R
import com.example.photo_album.utils.infrastructure.MainTopAppBar

@Composable
fun PhotoDetailScreen() {
    Scaffold(
        topBar = { MainTopAppBar(title = stringResource(id = R.string.photo_detail_top_app_bar)) },
        content = { ScreenContent() }
    )
}

@Composable
private fun ScreenContent(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Text(text = "Nothing to do here")
        }
    }
}