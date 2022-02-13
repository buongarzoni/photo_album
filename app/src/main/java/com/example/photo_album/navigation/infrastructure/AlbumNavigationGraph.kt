package com.example.photo_album.navigation.infrastructure

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.photo_album.album_list.infrastructure.AlbumListScreen
import com.example.photo_album.navigation.domain.ALBUM_GRAPH_ROUTE
import com.example.photo_album.navigation.domain.AlbumRoutes
import com.example.photo_album.photo_detail.infrastructure.PhotoDetailScreen
import com.example.photo_album.photo_list.infrastructure.PhotoListScreen


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
        composable(route = AlbumRoutes.PhotoList.route){
            PhotoListScreen(navController = navController)
        }
        composable(route = AlbumRoutes.PhotoDetail.route){
            PhotoDetailScreen()
        }
    }
}