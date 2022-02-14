package com.example.photo_album.navigation.infrastructure

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.photo_album.album_list.application.AlbumsViewModel
import com.example.photo_album.album_list.infrastructure.AlbumListScreen
import com.example.photo_album.navigation.domain.*
import com.example.photo_album.navigation.domain.PHOTO_DETAIL_KEY_PHOTO_NAME
import com.example.photo_album.photo_detail.infrastructure.PhotoDetailScreen
import com.example.photo_album.photo_list.infrastructure.PhotoListScreen


@ExperimentalCoilApi
@ExperimentalFoundationApi
fun NavGraphBuilder.albumNavigationGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AlbumRoutes.AlbumList.route,
        route = ALBUM_GRAPH_ROUTE
    ) {
        composable(route = AlbumRoutes.AlbumList.route){
            val parentEntry = remember { navController.getBackStackEntry(ALBUM_GRAPH_ROUTE)}
            val viewModel = hiltViewModel<AlbumsViewModel>(parentEntry)

            AlbumListScreen(navController = navController)
        }

        composable(
            route = AlbumRoutes.PhotoList.route,
            arguments = listOf(navArgument(PHOTO_LIST_KEY_ALBUM_NAME){
                type = NavType.StringType
            })
        ){
            val albumName = it.arguments?.getString(PHOTO_LIST_KEY_ALBUM_NAME)!!
            val parentEntry = remember { navController.getBackStackEntry(ALBUM_GRAPH_ROUTE)}
            val viewModel = hiltViewModel<AlbumsViewModel>(parentEntry)

            PhotoListScreen(navController = navController, albumName = albumName, viewModel = viewModel)
        }

        composable(route = AlbumRoutes.PhotoDetail.route){
            val albumName = it.arguments?.getString(PHOTO_DETAIL_KEY_ALBUM_NAME)!!
            val photoName = it.arguments?.getString(PHOTO_DETAIL_KEY_PHOTO_NAME)!!
            val parentEntry = remember { navController.getBackStackEntry(ALBUM_GRAPH_ROUTE)}
            val viewModel = hiltViewModel<AlbumsViewModel>(parentEntry)

            PhotoDetailScreen(photoName = photoName, albumName = albumName, viewModel = viewModel)
        }
    }
}