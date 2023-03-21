package com.example.cinema.domain

import com.example.cinema.data.CinemaCollectionRepository
import com.example.cinema.entity.dbCinema.FilmDBLocal
import javax.inject.Inject

class CollectionFilmUseCase @Inject constructor(
    private val cinemaCollectionRepository: CinemaCollectionRepository
) {
    suspend fun addFilmToCollection(film: FilmDBLocal, idCollection: Pair<Int,Boolean>) {
        cinemaCollectionRepository.addFilmToCollection(film, idCollection)
    }

  suspend  fun getSelectCollection(id:Int): List<Int> {
        return cinemaCollectionRepository.getSelectCollection(id)
    }
}
