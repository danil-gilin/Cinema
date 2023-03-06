package com.example.cinema.entity.searchFilm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchGenre(
    @Json(name = "genre")
    val genre: String
)