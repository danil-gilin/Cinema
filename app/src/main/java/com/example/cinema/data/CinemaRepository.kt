package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.gener.GenreName
import javax.inject.Inject

class CinemaRepository @Inject constructor() {

   suspend fun getCinemaGenre(genre:Int): AllCinema {
       return CinemaRetrofitObject.cinemaApi.getGenreFilm(genre)
    }
    suspend fun getCinemaGenreWithCountry(country:Int,genre:Int): AllCinema {
        return CinemaRetrofitObject.cinemaApi.getGenreWithCountryFilm(country,genre)
    }
    suspend fun getCinemaCountry(country:Int): AllCinema {
        return CinemaRetrofitObject.cinemaApi.getCountryFilm(country)
    }


    suspend fun getGenreAndCountry(): GenreName {
        return CinemaRetrofitObject.cinemaApi.getGenreAndCountry()
    }
}