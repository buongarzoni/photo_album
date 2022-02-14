package com.example.photo_album.photo_list.application

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photo_album.helpers.PhotosMock
import com.example.photo_album.photo_list.domain.AlbumState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumGalleryViewModel
@Inject
constructor(
): ViewModel() {
    private val _state: MutableState<AlbumState> = mutableStateOf(AlbumState(isLoading = true))
    val state: State<AlbumState> = _state

    init {
        getAlbumContent()
    }

    private fun getAlbumContent() {
        viewModelScope.launch(Dispatchers.IO){
            delay(5 * 1000)
            _state.value = AlbumState(photos = PhotosMock.fakePhotos)
        }
    }
}