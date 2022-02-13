package com.example.photo_album.navigation.domain

const val ALBUM_GRAPH_ROUTE = "album_graph"
const val PHOTO_LIST_KEY_ALBUM_NAME = "album_name"

sealed class AlbumRoutes(val route: String){
    object AlbumList: AlbumRoutes("album_list_screen")
    object PhotoList: AlbumRoutes("photo_list_screen/{$PHOTO_LIST_KEY_ALBUM_NAME}"){
        fun passAlbumName(albumName: String): String{
            return this.route.replace(oldValue = "{$PHOTO_LIST_KEY_ALBUM_NAME}", newValue = albumName)
        }
    }
    object PhotoDetail: AlbumRoutes("photo_detail_screen")
}
