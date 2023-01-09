package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import com.example.cinema.entity.gener.GenreName
import javax.inject.Inject

class CinemaRepository @Inject constructor() {

   suspend fun getCinemaGenre(genre: Int, page: Int): AllCinema {
       return CinemaRetrofitObject.cinemaApi.getGenreFilm(genre,page)
    }
    suspend fun getCinemaGenreWithCountry(country: Int, genre: Int, page: Int): AllCinema {
        return CinemaRetrofitObject.cinemaApi.getGenreWithCountryFilm(country,genre,page)
    }
    suspend fun getCinemaCountry(country: Int, page: Int): AllCinema {
        return CinemaRetrofitObject.cinemaApi.getCountryFilm(country,page)
    }
    suspend fun getGenreAndCountry(): GenreName {
        return CinemaRetrofitObject.cinemaApi.getGenreAndCountry()
    }
    suspend fun getPremieresFilm(year:Int,month:String): AllCinema {
        return CinemaRetrofitObject.cinemaApi.getPremieresFilm(year,month)
    }
    suspend fun getPopularFilm(type: String, page: Int): CinemaTop {
        return CinemaRetrofitObject.cinemaApi.getPopularFilm(type,page)
    }
    suspend fun getSerial(page: Int): AllCinema {
        return CinemaRetrofitObject.cinemaApi.getSerial(page)
    }
}