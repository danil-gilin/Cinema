package com.example.cinema.entity.dbCinema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WatchFilm")
data class WatchFilm(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "nameFilm")
    val nameFilm:String,
    @ColumnInfo(name = "img")
    val img:String,
    @ColumnInfo(name = "genre")
    val genre:String,
    @ColumnInfo(name = "rating")
    val rating: Double?,
    @ColumnInfo(name = "serial")
    val serial:Boolean,
)
