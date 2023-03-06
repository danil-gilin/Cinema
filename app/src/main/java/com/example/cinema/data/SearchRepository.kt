package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.filterEntity.Filter
import com.example.cinema.entity.filterEntity.FilterSearch
import com.example.cinema.entity.searchFilm.SearchItem
import javax.inject.Inject

class SearchRepository @Inject constructor() {

    suspend fun getSearchResult(query: FilterSearch): List<SearchItem> {
      return  CinemaRetrofitObject.searchApi.getSearchFilm(
            query.type,
            query.country,
            query.genre,
            query.yearFrom,
            query.yearTo,
            query.ratingFrom,
            query.ratingTo,
            query.sort,
            query.keyWord
        ).items
    }


}