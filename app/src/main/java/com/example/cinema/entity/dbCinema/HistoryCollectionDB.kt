package com.example.cinema.entity.dbCinema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class HistoryCollectionDB(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "idItem") val idItem: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "rating") val rating:Double,
    @ColumnInfo(name = "filmFlag") val filmFlag:Boolean,
    @ColumnInfo(name = "date_time") val dateTime: Long,
)
