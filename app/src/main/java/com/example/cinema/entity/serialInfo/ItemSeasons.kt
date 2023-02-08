package com.example.cinema.entity.serialInfo


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemSeasons(
    @Json(name = "episodes")
    val episodes: List<Episode>,
    @Json(name = "number")
    val number: Int
)