package com.example.cinema.entity.filterEntity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryFilter(
    @Json(name = "country")
    val country: String,
    @Json(name = "id")
    val id: Int
)