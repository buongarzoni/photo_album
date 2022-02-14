package com.example.photo_album.web.apis.jsonplaceholder.domain

import com.example.photo_album.album_list.domain.AlbumModel
import com.example.photo_album.photo_detail.domain.PhotoModel
import com.google.gson.annotations.SerializedName


data class AlbumsDTO(
    @SerializedName("albums") val albums: List<AlbumDTO>?
) {
    fun toModel(): List<AlbumModel> {
        return this.albums?.map { albumDTO -> albumDTO.toModel() }.orEmpty()
    }
}

data class AlbumDTO(
    @SerializedName("name") val name: String?,
    @SerializedName("photo_url") val photoUrl: String?,
    @SerializedName("photos") val photos: List<PhotosDTO>?
) {
    fun toModel(): AlbumModel {
        val photos = this.photos?.map {photosDTO ->  photosDTO.toModel()}
        return AlbumModel(
            name = this.name.orEmpty(),
            photoUrl = this.photoUrl.orEmpty(),
            photos = photos.orEmpty())

    }
}

data class PhotosDTO(
    @SerializedName("name") val name: String?,
    @SerializedName("photo_url") val photoUrl: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("date_created") val dateCreated: String?
) {
    fun toModel(): PhotoModel {
        return PhotoModel(
            name = this.name.orEmpty(),
            photo_url = this.photoUrl.orEmpty(),
            description = this.description.orEmpty(),
            dateCreated = this.dateCreated.orEmpty()
        )
    }
}