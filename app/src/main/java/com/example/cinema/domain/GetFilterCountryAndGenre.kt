package com.example.cinema.domain

import com.example.cinema.data.FilterRepository
import javax.inject.Inject

class GetFilterCountryAndGenre @Inject constructor(private val filterRepository: FilterRepository){
    suspend fun getFilterCountry()=filterRepository.getFilterCountry()
    suspend fun getFilterGenre()=filterRepository.getFilterGenre()
}