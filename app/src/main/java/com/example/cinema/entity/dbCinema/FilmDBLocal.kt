package com.example.cinema.entity.dbCinema

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "FilmDBLocal")
@Parcelize
class FilmDBLocal (
    @PrimaryKey
    @ColumnInfo(name = "id")
    override val id:Int,
    @ColumnInfo(name = "nameFilm")
    override val nameFilm:String,
    @ColumnInfo(name = "img")
    override val img:String,
    @ColumnInfo(name = "genre")
    override val genre:String,
    @ColumnInfo(name = "rating")
    override val rating:Double?,
    @ColumnInfo(name = "serial")
    override val serial:Boolean,
    @ColumnInfo(name = "watch")
    val watch:Boolean,
    @ColumnInfo(name = "year")
    val year:String,
):FilmDB,Parcelable
