package com.example.cinema.domain

import com.example.cinema.data.CinemaLocalRepository
import com.example.cinema.entity.dbCinema.WantToWatchFilm
import javax.inject.Inject

class WantToWatchFilmUseCase @Inject constructor (private val cinemaLocalRepository: CinemaLocalRepository) {
    suspend fun getWantToWatchFilmFilm(kinopoiskId: Int)=cinemaLocalRepository.getWantToWatchFilmFilm(kinopoiskId)

    suspend fun addWantToWatchFilmFilm(watchFilm: WantToWatchFilm)=cinemaLocalRepository.addWantToWatchFilmFilm(watchFilm)

    suspend fun delWantToWatchFilmFilm(kinopoiskId: Int) { cinemaLocalRepository.deleteWantToWatchFilmFilm(kinopoiskId) }
  suspend  fun getWantToWatchFilmSize(): Int {
      return  cinemaLocalRepository.getWantToWatchFilmSize()
    }

  suspend  fun getAllWantToWatchFilm()=cinemaLocalRepository.getAllWantToWatchFilm()
}