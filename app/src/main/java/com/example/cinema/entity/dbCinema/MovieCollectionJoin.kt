package com.example.cinema.entity.dbCinema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_collection_join",
    primaryKeys = ["movieId", "collectionId"],
    foreignKeys = [
        ForeignKey(entity = FilmDBLocal::class, parentColumns = ["id"], childColumns = ["movieId"]),
        ForeignKey(entity = CollectionFilms::class, parentColumns = ["id"], childColumns = ["collectionId"])
    ]
)
data class MovieCollectionJoin(
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "collectionId") val collectionId: Int
)
