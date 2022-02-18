package com.example.photo_album.web.apis.jsonplaceholder.application

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.photo_album.web.apis.jsonplaceholder.domain.AlbumsDTO
import com.example.photo_album.web.apis.util.infrastructure.Response
import com.google.gson.Gson
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class FakeJSONPlaceholderRepository @Inject
constructor(): JSONPlaceholderRepositoryInterface {

    override suspend fun getAlbums(): Response<AlbumsDTO> {
        val albums = Gson().fromJson(readJsonFromFile(), AlbumsDTO::class.java)
        return Response.Success(albums)
    }

    private fun readJsonFromFile(): String {
        val context = ApplicationProvider.getApplicationContext<Context>()
        var json = ""

        try {
            json = context.assets.open("apiResponse.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ignored: Exception) { }

        return json
    }
}