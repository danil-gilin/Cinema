package com.example.cinema.entity.gener


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryTitle(
    @Json(name = "country")
    val country: String,
    @Json(name = "id")
    val id: Int
)