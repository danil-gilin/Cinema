package com.example.cinema.domain

import com.example.cinema.data.CinemaLocalRepository
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import javax.inject.Inject

class HistoryLocalFilmUseCase  @Inject constructor(private val cinemaLocalRepository: CinemaLocalRepository) {
    suspend fun addHistoryLocal(history: HistoryCollectionDB) {
        cinemaLocalRepository.addHistory(history)
    }
}