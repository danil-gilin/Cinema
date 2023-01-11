package com.example.cinema.data.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.entity.dbCinema.WatchFilm

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatchFilm(watchFilm: WatchFilm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikeFilm(watchFilm: WatchFilm)

    @Query("SELECT * FROM WatchFilm WHERE id=:id")
    suspend fun getWatchFilm(id: Int): WatchFilm?

    @Query("SELECT id FROM WatchFilm")
    suspend fun getWatchFilmId(): List<Int>
}