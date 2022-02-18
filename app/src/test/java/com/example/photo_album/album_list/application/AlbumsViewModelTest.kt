package com.example.photo_album.album_list.application

import com.example.photo_album.CoroutineTestRule
import com.example.photo_album.R
import com.example.photo_album.web.apis.jsonplaceholder.application.JSONPlaceholderRepository
import com.example.photo_album.web.apis.jsonplaceholder.domain.AlbumDTO
import com.example.photo_album.web.apis.jsonplaceholder.domain.AlbumsDTO
import com.example.photo_album.web.apis.util.infrastructure.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class AlbumsViewModelTest{

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val repository: JSONPlaceholderRepository = mock(JSONPlaceholderRepository::class.java)


    @Test
    fun `view model state must be loading when instantiate`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val viewModel = AlbumsViewModel(repository = repository)

        assertTrue(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.errorMessage)
        assertTrue(viewModel.state.value.albums.isEmpty())
    }

    @Test
    fun `view model state must have repository error message when an error has occurred`() = coroutineTestRule.testDispatcher.runBlockingTest {
        `when`(repository.getAlbums()).thenReturn(Response.Error(errorMessage = "any error"))
        val viewModel = AlbumsViewModel(repository = repository)

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(viewModel.state.value.errorMessage, R.string.albums_view_model_default_error_message)
        assertTrue(viewModel.state.value.albums.isEmpty())
    }

    @Test
    fun `view model state must have error message for case where api response is null`() = coroutineTestRule.testDispatcher.runBlockingTest {
        `when`(repository.getAlbums()).thenReturn(Response.Success(AlbumsDTO(null)))
        val viewModel = AlbumsViewModel(repository = repository)

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(viewModel.state.value.errorMessage, R.string.albums_view_model_server_error)
        assertTrue(viewModel.state.value.albums.isEmpty())
    }

    @Test
    fun `view model state must have error message of empty albums in db message`() = coroutineTestRule.testDispatcher.runBlockingTest {
        `when`(repository.getAlbums()).thenReturn(Response.Success(AlbumsDTO(albums = emptyList())))
        val viewModel = AlbumsViewModel(repository = repository)

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(viewModel.state.value.errorMessage, R.string.albums_view_model_no_images_in_db_error)
        assertTrue(viewModel.state.value.albums.isEmpty())
    }

    @Test
    fun `view model state must have albums when api response is successful`() = coroutineTestRule.testDispatcher.runBlockingTest {
        `when`(repository.getAlbums())
            .thenReturn(Response.Success(
                generateSuccessfulApiResponse()
            ))

        val viewModel = AlbumsViewModel(repository = repository)

        assertFalse(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.errorMessage)
        assertTrue(viewModel.state.value.albums.isNotEmpty())
    }

    private fun generateSuccessfulApiResponse(): AlbumsDTO {
        return AlbumsDTO(
            albums = listOf(AlbumDTO(
                name = "album 1",
                photoUrl = "any url",
                photos = emptyList())
            )
        )
    }
}
