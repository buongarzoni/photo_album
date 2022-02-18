package com.example.photo_album.album_list.application

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun getAlbums() {
        viewModelScope.launch {
            val response = repository.getAlbums()

            response.errorMessage?.let { errorMessage ->
                _state.value = AlbumsState(errorMessage = errorMessage)
            }

            response.data?.let { albumsDTO ->
                updateStateWithAlbumsDTOValue(albumsDTO = albumsDTO)
            }
        }
    }

    private fun updateStateWithAlbumsDTOValue(albumsDTO: AlbumsDTO) {
        when {
            albumsDTO.albums == null -> _state.value = AlbumsState(errorMessage = "Error en el servidor, por favor reintente luego")
            albumsDTO.albums.isEmpty() -> _state.value = AlbumsState(errorMessage = "Actualmente no hay álbumes en la base de datos")
            else -> _state.value = AlbumsState(albums = albumsDTO.toModel())
        }
    }
}