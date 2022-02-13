package com.example.photo_album.album_list.application

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photo_album.album_list.domain.AlbumsState
import com.example.photo_album.helpers.AlbumsMock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel
@Inject
constructor(
): ViewModel() {
    private val _state: MutableState<AlbumsState> = mutableStateOf(AlbumsState(isLoading = true))
    val state: State<AlbumsState> = _state

    init {
        getAlbums()
    }

    private fun getAlbums() {
        viewModelScope.launch(Dispatchers.IO){
            delay(5 * 1000)
            _state.value = AlbumsState(albums = AlbumsMock.fakeAlbums)
        }
    }
}