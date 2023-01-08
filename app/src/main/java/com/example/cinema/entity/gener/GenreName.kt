package com.example.cinema.entity.gener


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreName(
    @Json(name = "countries")
    val countries: List<CountryTitle>,
    @Json(name = "genres")
    val genres: List<GenreTitle>
)