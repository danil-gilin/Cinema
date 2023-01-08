package com.example.cinema.domain

import com.example.cinema.data.CinemaRepository
import javax.inject.Inject

class GetGenreNameUseCase @Inject constructor(private val repository: CinemaRepository) {
    suspend fun getGenreName()=repository.getGenreAndCountry()
}