package com.example.cinema.entity.fullInfoActor


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Film(
    @Json(name = "description")
    val description: String?,
    @Json(name = "filmId")
    val filmId: Int?,
    @Json(name = "general")
    val general: Boolean?,
    @Json(name = "nameEn")
    val nameEn: String?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "professionKey")
    val professionKey: String?,
    @Json(name = "rating")
    val rating: String?
)