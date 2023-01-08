package com.example.cinema.entity.gener


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreTitle(
    @Json(name = "genre")
    val genre: String,
    @Json(name = "id")
    val id: Int
)