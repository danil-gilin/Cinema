package com.example.cinema.entity.filterEntity

data class FilterSearch (
    var type:String,
    var country: Int,
    var genre:Int,
    var yearFrom: Int,
    var yearTo: Int,
    var ratingFrom: Int,
    var ratingTo: Int,
    var sort: String,
    var watch: Boolean,
    var keyWord : String
)