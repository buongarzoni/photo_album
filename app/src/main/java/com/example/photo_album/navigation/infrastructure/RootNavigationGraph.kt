package com.example.photo_album.navigation.infrastructure

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.photo_album.navigation.domain.ALBUM_GRAPH_ROUTE

const val ROOT_GRAPH_ROUTE = "root_graph"

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ALBUM_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        albumNavigationGraph(navController = navController)
    }
}