package com.example.photo_album.di

import com.example.photo_album.BuildConfig
import com.example.photo_album.web.apis.jsonplaceholder.application.JSONPlaceholderRepository
import com.example.photo_album.web.apis.jsonplaceholder.application.JSONPlaceholderRepositoryInterface
import com.example.photo_album.web.apis.jsonplaceholder.domain.Constants.JSON_PLACEHOLDER_BASE_URL
import com.example.photo_album.web.apis.jsonplaceholder.infrastructure.JSONPlaceholderAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJSONPlaceholderRepository(
        api: JSONPlaceholderAPI
    ): JSONPlaceholderRepositoryInterface = JSONPlaceholderRepository(api)

    @Singleton
    @Provides
    fun provideJSONPlaceholderApi(
        retrofit: Retrofit
    ): JSONPlaceholderAPI {
        return retrofit.create(JSONPlaceholderAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(JSON_PLACEHOLDER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(client)
            .build()
    }
}
