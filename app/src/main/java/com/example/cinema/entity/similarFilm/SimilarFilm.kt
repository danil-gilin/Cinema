package com.example.cinema.entity.similarFilm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimilarFilm(
    @Json(name = "items")
    val items: List<SimilarItem>,
    @Json(name = "total")
    val total: Int
)