package com.example.cinema.entity.cinema


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllCinema(
    @Json(name = "items")
    val items: List<Cinema>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPages")
    val totalPages: Int?
)