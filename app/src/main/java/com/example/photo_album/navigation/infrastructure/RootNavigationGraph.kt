package com.example.photo_album.navigation.infrastructure

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.photo_album.navigation.domain.ALBUM_GRAPH_ROUTE
import com.google.accompanist.navigation.animation.AnimatedNavHost

const val ROOT_GRAPH_ROUTE = "root_graph"

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun RootNavigationGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = ALBUM_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        albumNavigationGraph(navController = navController)
    }
}