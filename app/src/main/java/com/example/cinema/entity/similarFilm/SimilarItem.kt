package com.example.cinema.entity.similarFilm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimilarItem(
    @Json(name = "filmId")
    val filmId: Int,
    @Json(name = "nameEn")
    val nameEn: String?,
    @Json(name = "nameOriginal")
    val nameOriginal: String?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "posterUrl")
    val posterUrl: String,
    @Json(name = "posterUrlPreview")
    val posterUrlPreview: String,
    @Json(name = "relationType")
    val relationType: String?
)