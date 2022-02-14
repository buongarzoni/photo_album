package com.example.photo_album.web.apis.jsonplaceholder.infrastructure

import com.example.photo_album.web.apis.jsonplaceholder.domain.AlbumsDTO
import retrofit2.http.GET

interface JSONPlaceholderAPI {

    @GET("db")
    suspend fun getAlbums(): AlbumsDTO
}
