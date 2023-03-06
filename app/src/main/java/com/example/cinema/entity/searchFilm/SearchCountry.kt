package com.example.cinema.entity.searchFilm


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchCountry(
    @Json(name = "country")
    val country: String
)