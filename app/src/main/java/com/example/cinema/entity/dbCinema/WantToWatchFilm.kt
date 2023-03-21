package com.example.cinema.entity.dbCinema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WantToWatchFilm")
data class WantToWatchFilm(
    @PrimaryKey
    @ColumnInfo(name = "id")
   override val id:Int,
    @ColumnInfo(name = "nameFilm")
    override   val nameFilm:String,
    @ColumnInfo(name = "img")
    override  val img:String,
    @ColumnInfo(name = "genre")
    override  val genre:String,
    @ColumnInfo(name = "rating")
    override  val rating: Double?,
    @ColumnInfo(name = "serial")
    override  val serial:Boolean,
):FilmDB
