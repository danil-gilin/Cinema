package com.example.cinema.data.bd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.entity.dbCinema.LikeFilm
import com.example.cinema.entity.dbCinema.WantToWatchFilm
import com.example.cinema.entity.dbCinema.WatchFilm

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatchFilm(watchFilm: WatchFilm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikeFilm(watchFilm: LikeFilm)

    @Query("SELECT * FROM WatchFilm WHERE id=:id")
    suspend fun getWatchFilm(id: Int): WatchFilm?

    @Query("SELECT id FROM WatchFilm")
    suspend fun getWatchFilmId(): List<Int>

    @Query("DELETE FROM WatchFilm WHERE id=:id")
    suspend fun deleteWatchFilm(id: Int)


    @Query("SELECT * FROM LikeFilm WHERE id=:id")
    suspend fun getLikeFilm(id: Int): LikeFilm?

    @Query("DELETE FROM LikeFilm WHERE id=:id")
    suspend fun deleteLikeFilm(id: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWantToWatchFilm(watchFilm: WantToWatchFilm)

    @Query("SELECT * FROM WantToWatchFilm WHERE id=:id")
    suspend fun getWantToWatchFilm(id: Int): WantToWatchFilm?

    @Query("DELETE FROM WantToWatchFilm WHERE id=:id")
    suspend fun deleteWantToWatchFilm(id: Int)
}