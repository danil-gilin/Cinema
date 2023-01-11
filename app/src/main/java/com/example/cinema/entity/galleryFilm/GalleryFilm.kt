package com.example.cinema.entity.galleryFilm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryFilm(
    @Json(name = "items")
    val items: List<GalleryItem>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPages")
    val totalPages: Int
)