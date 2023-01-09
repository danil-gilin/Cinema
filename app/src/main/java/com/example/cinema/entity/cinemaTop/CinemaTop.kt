package com.example.cinema.entity.cinemaTop


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CinemaTop(
    @Json(name = "films")
    val films: List<Film>,
    @Json(name = "pagesCount")
    val pagesCount: Int
)