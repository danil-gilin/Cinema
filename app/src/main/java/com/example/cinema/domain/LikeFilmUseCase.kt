package com.example.cinema.domain

import com.example.cinema.data.CinemaLocalRepository
import com.example.cinema.entity.dbCinema.LikeFilm
import com.example.cinema.entity.dbCinema.WatchFilm
import javax.inject.Inject

class LikeFilmUseCase @Inject constructor (private val cinemaLocalRepository: CinemaLocalRepository) {
    suspend fun getLikeFilm(kinopoiskId: Int)=cinemaLocalRepository.getLikeFilm(kinopoiskId)

    suspend fun addLikeFilm(watchFilm: LikeFilm)=cinemaLocalRepository.addLikeFilm(watchFilm)

    suspend fun delLikeFilm(kinopoiskId: Int) { cinemaLocalRepository.deleteLikeFilm(kinopoiskId) }
}