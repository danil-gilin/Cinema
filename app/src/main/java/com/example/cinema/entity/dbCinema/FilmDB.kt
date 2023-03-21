package com.example.cinema.entity.dbCinema

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

interface FilmDB {
    val id:Int
    val nameFilm:String
    val img:String
    val genre:String
    val rating:Double?
    val serial:Boolean
}