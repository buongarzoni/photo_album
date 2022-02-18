package com.example.photo_album.album_list.domain

data class AlbumsState(
    val isLoading: Boolean = false,
    val albums: List<AlbumModel> = emptyList(),
    val errorMessage: Int? = null
)
