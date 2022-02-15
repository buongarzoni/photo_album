package com.example.photo_album.navigation.infrastructure

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.photo_album.album_list.application.AlbumsViewModel
import com.example.photo_album.album_list.infrastructure.AlbumListScreen
import com.example.photo_album.navigation.domain.*
import com.example.photo_album.navigation.domain.PHOTO_DETAIL_KEY_PHOTO_NAME
import com.example.photo_album.photo_detail.infrastructure.PhotoDetailScreen
import com.example.photo_album.photo_list.infrastructure.PhotoListScreen


@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
fun NavGraphBuilder.albumNavigationGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AlbumRoutes.AlbumList.route,
        route = ALBUM_GRAPH_ROUTE
    ) {
        composable(
            route = AlbumRoutes.AlbumList.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() }
        ){
            val parentEntry = remember { navController.getBackStackEntry(ALBUM_GRAPH_ROUTE)}
            val viewModel = hiltViewModel<AlbumsViewModel>(parentEntry)

            AlbumListScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = AlbumRoutes.PhotoList.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            popExitTransition = { exitTransition() },
            arguments = listOf(navArgument(PHOTO_LIST_KEY_ALBUM_NAME){
                type = NavType.StringType
            })
        ){
            val albumName = it.arguments?.getString(PHOTO_LIST_KEY_ALBUM_NAME)!!
            val parentEntry = remember { navController.getBackStackEntry(ALBUM_GRAPH_ROUTE)}
            val viewModel = hiltViewModel<AlbumsViewModel>(parentEntry)

            PhotoListScreen(navController = navController, albumName = albumName, viewModel = viewModel)
        }

        composable(
            route = AlbumRoutes.PhotoDetail.route,
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ){
            val albumName = it.arguments?.getString(PHOTO_DETAIL_KEY_ALBUM_NAME)!!
            val photoName = it.arguments?.getString(PHOTO_DETAIL_KEY_PHOTO_NAME)!!
            val parentEntry = remember { navController.getBackStackEntry(ALBUM_GRAPH_ROUTE)}
            val viewModel = hiltViewModel<AlbumsViewModel>(parentEntry)

            PhotoDetailScreen(photoName = photoName, albumName = albumName, viewModel = viewModel)
        }
    }
}

private fun enterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(700))
}

private fun exitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(700))
}