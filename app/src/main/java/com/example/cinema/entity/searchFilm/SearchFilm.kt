package com.example.cinema.entity.searchFilm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchFilm(
    @Json(name = "items")
    val items: List<SearchItem>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPages")
    val totalPages: Int
)