package com.example.cinema.entity.fullInfoActor


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Spouse(
    @Json(name = "children")
    val children: Int?,
    @Json(name = "divorced")
    val divorced: Boolean?,
    @Json(name = "divorcedReason")
    val divorcedReason: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "personId")
    val personId: Int?,
    @Json(name = "relation")
    val relation: String?,
    @Json(name = "sex")
    val sex: String?,
    @Json(name = "webUrl")
    val webUrl: String?
)