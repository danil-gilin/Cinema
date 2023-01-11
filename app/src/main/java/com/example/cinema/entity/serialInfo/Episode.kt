package com.example.cinema.entity.serialInfo


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    @Json(name = "episodeNumber")
    val episodeNumber: Int,
    @Json(name = "nameEn")
    val nameEn: String?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "releaseDate")
    val releaseDate: String?,
    @Json(name = "seasonNumber")
    val seasonNumber: Int,
    @Json(name = "synopsis")
    val synopsis: String?
)