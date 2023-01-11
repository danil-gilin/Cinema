package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.filmInfo.FilmInfo
import javax.inject.Inject

class CinemaFullInfoRepostory @Inject constructor() {
   suspend fun getFilmFullInfo(id:Int):FilmInfo{
     return CinemaRetrofitObject.cinemaFullInfoApi.getFullInfo(id)
    }
}