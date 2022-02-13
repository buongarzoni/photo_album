package com.example.photo_album.navigation.domain

const val ALBUM_GRAPH_ROUTE = "album_graph"

sealed class AlbumRoutes(val route: String){
    object AlbumList: AlbumRoutes("album_list_screen")
    object PhotoList: AlbumRoutes("photo_list_screen")
    object PhotoDetail: AlbumRoutes("photo_detail_screen")
}
