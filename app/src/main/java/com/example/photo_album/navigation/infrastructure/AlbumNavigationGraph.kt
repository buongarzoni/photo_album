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
import com.example.photo_album.album_list.infrastructure.AlbumListScreen
import com.example.photo_album.navigation.domain.ALBUM_GRAPH_ROUTE
import com.example.photo_album.navigation.domain.PHOTO_LIST_KEY_ALBUM_NAME
import com.example.photo_album.navigation.domain.AlbumRoutes
import com.example.photo_album.navigation.domain.PHOTO_DETAIL_KEY_PHOTO_NAME
import com.example.photo_album.photo_detail.infrastructure.PhotoDetailScreen
import com.example.photo_album.photo_list.application.AlbumGalleryViewModel
import com.example.photo_album.photo_list.infrastructure.PhotoListScreen


@ExperimentalFoundationApi
fun NavGraphBuilder.albumNavigationGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AlbumRoutes.AlbumList.route,
        route = ALBUM_GRAPH_ROUTE
    ) {
        composable(route = AlbumRoutes.AlbumList.route){
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
            val viewModel = hiltViewModel<AlbumGalleryViewModel>(parentEntry)

            PhotoListScreen(navController = navController, albumName = albumName, viewModel = viewModel)
        }

        composable(route = AlbumRoutes.PhotoDetail.route){
            val photoName = it.arguments?.getString(PHOTO_DETAIL_KEY_PHOTO_NAME)!!
            val parentEntry = remember { navController.getBackStackEntry(ALBUM_GRAPH_ROUTE)}
            val viewModel = hiltViewModel<AlbumGalleryViewModel>(parentEntry)

            PhotoDetailScreen(photoName = photoName, viewModel = viewModel)
        }
    }
}