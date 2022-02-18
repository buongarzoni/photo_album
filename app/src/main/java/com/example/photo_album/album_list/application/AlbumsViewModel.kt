package com.example.photo_album.album_list.application

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photo_album.R
import com.example.photo_album.album_list.domain.AlbumsState
import com.example.photo_album.web.apis.jsonplaceholder.application.JSONPlaceholderRepositoryInterface
import com.example.photo_album.web.apis.jsonplaceholder.domain.AlbumsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel
@Inject
constructor(
    private val repository: JSONPlaceholderRepositoryInterface
): ViewModel() {
    private val _state: MutableState<AlbumsState> = mutableStateOf(AlbumsState(isLoading = true))
    val state: State<AlbumsState> = _state

    init {
        getAlbums()
    }

    fun getAlbums() {
        _state.value = AlbumsState(isLoading = true)
        viewModelScope.launch {
            val response = repository.getAlbums()

            response.errorMessage?.let {
                _state.value = AlbumsState(errorMessage = R.string.albums_view_model_default_error_message)
            }

            response.data?.let { albumsDTO ->
                updateStateWithAlbumsDTOValue(albumsDTO = albumsDTO)
            }
        }
    }

    private fun updateStateWithAlbumsDTOValue(albumsDTO: AlbumsDTO) {
        when {
            albumsDTO.albums == null -> _state.value = AlbumsState(errorMessage = R.string.albums_view_model_server_error)
            albumsDTO.albums.isEmpty() -> _state.value = AlbumsState(errorMessage = R.string.albums_view_model_no_images_in_db_error)
            else -> _state.value = AlbumsState(albums = albumsDTO.toModel())
        }
    }
}