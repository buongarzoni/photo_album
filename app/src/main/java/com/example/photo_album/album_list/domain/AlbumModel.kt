package com.example.photo_album.album_list.domain

import com.example.photo_album.photo_detail.domain.PhotoModel

data class AlbumModel(
    val name: String,
    val photoUrl: String,
    val photos: List<PhotoModel>
)