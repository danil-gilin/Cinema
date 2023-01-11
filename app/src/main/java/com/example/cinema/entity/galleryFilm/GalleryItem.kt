package com.example.cinema.entity.galleryFilm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryItem(
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "previewUrl")
    val previewUrl: String
)