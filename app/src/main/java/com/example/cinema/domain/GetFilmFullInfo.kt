package com.example.cinema.domain

import com.example.cinema.data.CinemaFullInfoRepostory
import javax.inject.Inject

class GetFilmFullInfo @Inject constructor(private val cinemaFullInfoRepostory: CinemaFullInfoRepostory) {

    suspend fun getFilmInfo(id: Int) = cinemaFullInfoRepostory.getFilmFullInfo(id)

    suspend fun getSerialInfo(id: Int) = cinemaFullInfoRepostory.getInfoForSerial(id)

    suspend fun getSimilarFilms(id: Int) = cinemaFullInfoRepostory.getSimilarFilm(id)

    suspend fun getActorAndWorker(id: Int) = cinemaFullInfoRepostory.getActorAndWroker(id)

    suspend fun getGalerryFilm(id: Int) = cinemaFullInfoRepostory.getGallery(id)

}