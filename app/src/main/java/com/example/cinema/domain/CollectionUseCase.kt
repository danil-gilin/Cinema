package com.example.cinema.domain

import com.example.cinema.data.CinemaCollectionRepository
import javax.inject.Inject

class CollectionUseCase @Inject constructor(
    private val cinemaCollectionRepository: CinemaCollectionRepository
) {
    suspend fun addCollection(nameCollection: String):Int {
      return  cinemaCollectionRepository.addCollection(nameCollection)
    }

    suspend fun getAllCollection() = cinemaCollectionRepository.getAllCollection()
    suspend fun deleteCollection(id: Int) { cinemaCollectionRepository.deleteCollection(id) }

    suspend fun getCollectionNameById(id: Int)=cinemaCollectionRepository.getCollectionNameById(id)

}