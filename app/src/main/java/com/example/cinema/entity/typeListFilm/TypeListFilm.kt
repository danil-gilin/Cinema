package com.example.cinema.entity.typeListFilm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeListFilm(
    var name:String,
    var genreId:Int?=null,
    var countryId:Int?=null,
    var topFilmType:String?=null,
    var year:Int?=null,
    var month:String?=null,
    var serial:Boolean?=null,
    var semilarFilmId:Int?=null,
): Parcelable
