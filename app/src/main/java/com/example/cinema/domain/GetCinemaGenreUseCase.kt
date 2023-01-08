package com.example.cinema.domain

import com.example.cinema.data.CinemaRepository
import com.example.cinema.entity.cinema.AllCinema
import javax.inject.Inject

class GetCinemaGenreUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun getCinemaGenre(genre:Int):AllCinema {
        return repository.getCinemaGenre(genre)
    }

    suspend fun getCinemaGenreWithCountry(country:Int,genre:Int):AllCinema {
        return repository.getCinemaGenreWithCountry(country, genre)
    }

    suspend fun getCountryCinema(country:Int):AllCinema {
        return repository.getCinemaCountry(country)
    }
}