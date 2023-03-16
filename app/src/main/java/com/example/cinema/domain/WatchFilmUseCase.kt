package com.example.cinema.domain

import com.example.cinema.data.CinemaLocalRepository
import com.example.cinema.entity.dbCinema.WatchFilm
import javax.inject.Inject

class WatchFilmUseCase @Inject constructor (private val cinemaLocalRepository: CinemaLocalRepository) {
    suspend fun getWatchFilmId()=cinemaLocalRepository.getWatchFilmId()

    suspend fun getWatchFilm(kinopoiskId: Int)=cinemaLocalRepository.getWatchFilm(kinopoiskId)

    suspend fun addWatchFilm(watchFilm: WatchFilm)=cinemaLocalRepository.addWatchFilm(watchFilm)

    suspend fun delWatchFilm(kinopoiskId: Int) { cinemaLocalRepository.deleteWatchFilm(kinopoiskId) }
}