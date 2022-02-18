package com.example.photo_album.web.apis.jsonplaceholder.application

import com.example.photo_album.web.apis.jsonplaceholder.domain.AlbumsDTO
import com.example.photo_album.web.apis.util.infrastructure.Response

interface JSONPlaceholderRepositoryInterface {
    suspend fun getAlbums(): Response<AlbumsDTO>
}