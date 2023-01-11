package com.example.cinema.data

import com.example.cinema.data.bd.CinemaDao
import com.example.cinema.entity.dbCinema.WatchFilm
import javax.inject.Inject

class CinemaLocalRepository @Inject constructor(
    private val cinemaDao: CinemaDao
) {
    suspend fun addWatchFilm(watchFilm: WatchFilm) {
        cinemaDao.addWatchFilm(watchFilm)
    }
    suspend fun addLikeFilm(watchFilm: WatchFilm) {
        cinemaDao.addLikeFilm(watchFilm)
    }
    suspend fun getWatchFilm(id: Int): WatchFilm? {
        return cinemaDao.getWatchFilm(id)
    }
    suspend fun getWatchFilmId(): List<Int> {
        return cinemaDao.getWatchFilmId()
    }
}