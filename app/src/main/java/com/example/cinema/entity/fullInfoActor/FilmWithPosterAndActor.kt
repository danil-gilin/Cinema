package com.example.cinema.entity.fullInfoActor

import com.squareup.moshi.Json

data class FilmWithPosterAndActor (
    val description: String?,
    val filmId: Int,
    val general: Boolean?,
    val nameEn: String?,
    val nameRu: String?,
    val professionKey: String?,
    val rating: String?,
    val posterUrlPreview: String?,
    val year: String?=null,
    val genre: String?=null,
    )
