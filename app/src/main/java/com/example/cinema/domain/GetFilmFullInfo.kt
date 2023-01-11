package com.example.cinema.domain

import com.example.cinema.data.CinemaFullInfoRepostory
import javax.inject.Inject

class GetFilmFullInfo @Inject constructor(private val cinemaFullInfoRepostory: CinemaFullInfoRepostory) {
    suspend fun getFilmInfo(id: Int) = cinemaFullInfoRepostory.getFilmFullInfo(id)
}