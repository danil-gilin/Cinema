package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.filmInfo.FilmInfo
import com.example.cinema.entity.serialInfo.InfoSeasons
import javax.inject.Inject

class CinemaFullInfoRepostory @Inject constructor() {
   suspend fun getFilmFullInfo(id:Int):FilmInfo{
     return CinemaRetrofitObject.cinemaFullInfoApi.getFullInfo(id)
    }

    suspend fun getInfoForSerial(id:Int): InfoSeasons {
        return CinemaRetrofitObject.cinemaFullInfoApi.getInfoForSerial(id)
    }

    suspend fun getActorAndWroker(id: Int)=CinemaRetrofitObject.cinemaFullInfoApi.getListActor(id)
    suspend fun getGallery(id: Int)=CinemaRetrofitObject.cinemaFullInfoApi.getListGallery(id)
    suspend fun getSimilarFilm(id: Int)=CinemaRetrofitObject.cinemaFullInfoApi.getListSimilarFilm(id)

}