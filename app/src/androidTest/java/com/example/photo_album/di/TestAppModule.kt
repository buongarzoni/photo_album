package com.example.photo_album.di

import com.example.photo_album.web.apis.jsonplaceholder.application.FakeJSONPlaceholderRepository
import com.example.photo_album.web.apis.jsonplaceholder.application.JSONPlaceholderRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    fun provideJSONPlaceholderRepository(
    ): JSONPlaceholderRepositoryInterface = FakeJSONPlaceholderRepository()
}
