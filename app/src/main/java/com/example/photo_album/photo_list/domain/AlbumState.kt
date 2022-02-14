package com.example.photo_album.photo_list.domain

data class AlbumState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val errorMessage: String = ""
)
