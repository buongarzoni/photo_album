package com.example.photo_album.album_list.domain

data class AlbumsState(
    val isLoading: Boolean = false,
    val albums: List<Album> = emptyList(),
    val errorMessage: String = ""
)
