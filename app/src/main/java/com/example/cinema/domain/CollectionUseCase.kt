package com.example.cinema.domain

import com.example.cinema.data.CinemaCollectionRepository
import javax.inject.Inject

class CollectionUseCase @Inject constructor(
    private val cinemaCollectionRepository: CinemaCollectionRepository
) {
    suspend fun addCollection(nameCollection: String) {
        cinemaCollectionRepository.addCollection(nameCollection)
    }

    suspend fun getAllCollection() = cinemaCollectionRepository.getAllCollection()

}