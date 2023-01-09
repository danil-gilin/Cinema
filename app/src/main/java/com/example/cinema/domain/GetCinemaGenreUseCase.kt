package com.example.cinema.domain

import com.example.cinema.data.CinemaRepository
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import javax.inject.Inject

class GetCinemaGenreUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun getCinemaGenre(genre:Int,page:Int):AllCinema {
        return repository.getCinemaGenre(genre,page)
    }

    suspend fun getCinemaGenreWithCountry(country:Int,genre:Int,page:Int):AllCinema {
        return repository.getCinemaGenreWithCountry(country, genre, page)
    }

    suspend fun getCountryCinema(country:Int,page:Int):AllCinema {
        return repository.getCinemaCountry(country,page)
    }

    suspend fun getPremieresFilm(year:Int,month:String):AllCinema {
        return repository.getPremieresFilm(year, month)
    }

    suspend fun getPopularFilm(type: String, page: Int): CinemaTop {
        return repository.getPopularFilm(type,page)
    }

    suspend fun getSerial(page: Int):AllCinema {
        return repository.getSerial(page)
    }
}