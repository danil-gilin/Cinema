package com.example.cinema.entity.filterEntity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreAndCountryFilter(
    @Json(name = "countries")
    val countries: List<CountryFilter>,
    @Json(name = "genres")
    val genres: List<GenreFilter>
)