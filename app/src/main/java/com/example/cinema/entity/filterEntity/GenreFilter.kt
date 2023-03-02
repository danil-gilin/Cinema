package com.example.cinema.entity.filterEntity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreFilter(
    @Json(name = "genre")
    val genre: String,
    @Json(name = "id")
    val id: Int
)