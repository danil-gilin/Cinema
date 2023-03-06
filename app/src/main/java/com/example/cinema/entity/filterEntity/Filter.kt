package com.example.cinema.entity.filterEntity

data class Filter(
    var type:TypeFilmFilter=TypeFilmFilter.ALL,
    var country: String = "",
    var genre: String = "",
    var yearFrom: String = "",
    var yearTo: String = "",
    var ratingFrom: Int = 0,
    var ratingTo: Int = 0,
    var sort: SortFilter=SortFilter.RATING,
    var watch: Boolean = false
)
