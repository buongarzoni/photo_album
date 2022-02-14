package com.example.photo_album.photo_list.domain

import com.example.photo_album.photo_detail.domain.PhotoModel

data class AlbumState(
    val isLoading: Boolean = false,
    val photos: List<PhotoModel> = emptyList(),
    val errorMessage: String = ""
)
