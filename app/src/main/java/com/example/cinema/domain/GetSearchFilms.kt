package com.example.cinema.domain

import com.example.cinema.data.SearchRepository
import com.example.cinema.entity.filterEntity.FilterSearch
import javax.inject.Inject

class GetSearchFilms @Inject constructor(private val searchRepository: SearchRepository) {
    suspend fun getSerachFilmsPagging(query: FilterSearch,page:Int) = searchRepository.getSearchResultPage(query,page)
}