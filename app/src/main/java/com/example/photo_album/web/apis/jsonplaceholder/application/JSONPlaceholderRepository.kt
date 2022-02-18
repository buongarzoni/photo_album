package com.example.photo_album.web.apis.jsonplaceholder.application

import com.example.photo_album.web.apis.jsonplaceholder.domain.AlbumsDTO
import com.example.photo_album.web.apis.jsonplaceholder.infrastructure.JSONPlaceholderAPI
import com.example.photo_album.web.apis.util.infrastructure.Response
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class JSONPlaceholderRepository @Inject
constructor(private val api: JSONPlaceholderAPI): JSONPlaceholderRepositoryInterface {

    override suspend fun getAlbums(): Response<AlbumsDTO> {
        val response = try {
            api.getAlbums()
        } catch (exception: Exception) {
            return Response.Error("$exception")
        }
        return Response.Success(response)
    }
}
