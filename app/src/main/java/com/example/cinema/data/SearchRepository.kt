package com.example.cinema.data

import android.util.Log
import com.example.cinema.data.bd.CinemaDao
import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.filterEntity.Filter
import com.example.cinema.entity.filterEntity.FilterSearch
import com.example.cinema.entity.searchFilm.SearchItem
import javax.inject.Inject

class SearchRepository @Inject constructor( private val cinemaDao: CinemaDao) {

    suspend fun getSearchResultPage(query: FilterSearch,page:Int): List<SearchItem> {
        val listSearch=  CinemaRetrofitObject.searchApi.getSearchFilm(
            query.type,
            query.country,
            query.genre,
            query.yearFrom,
            query.yearTo,
            query.ratingFrom,
            query.ratingTo,
            query.sort,
            query.keyWord,
            page
        ).items
        if (!query.watch) {
            val listWatch =  cinemaDao.getWatchFilmId()
            return listSearch.filter { !listWatch.contains(it.kinopoiskId) }
        }else{
            return listSearch
        }
    }


}