package com.example.cinema.entity.serialInfo


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoSeasons(
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "total")
    val total: Int
)