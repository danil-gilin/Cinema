package com.example.cinema.presenter.home.searchPage.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.domain.FilterLocalUseCase
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.domain.GetSearchFilms
import com.example.cinema.domain.WatchFilmUseCase
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.entity.dbCinema.WatchFilm
import com.example.cinema.entity.filterEntity.*
import com.example.cinema.entity.searchFilm.SearchItem
import com.example.cinema.service.adapterSearchFilms.SearchPaggingSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchFilms: GetSearchFilms,
    private val filterLocalUseCase: FilterLocalUseCase,
    private val getFilterCountryAndGenre: GetFilterCountryAndGenre,
    private val watchFilmUseCase: WatchFilmUseCase
) : ViewModel() {
    private val _listwatchesFilm = Channel<List<Int>> { }
    val listwatchesFilm = _listwatchesFilm .receiveAsFlow()


    val filterForSearch = FilterSearch(
        "ALL",
        34,
        13,
        2000,
        2017,
        5,
        10,
        "RATING",
        false,
        "")

    var listSearchPaggin: Flow<PagingData<SearchItem>> = Pager(
    config = PagingConfig(pageSize = 20),
    initialKey = null,
    pagingSourceFactory = { SearchPaggingSource(filterForSearch,getSearchFilms) }
    ).flow.cachedIn(viewModelScope)


    fun getWatchesFilm(){
        viewModelScope.launch {
            val listWatchesFilm = watchFilmUseCase.getWatchFilmId()
            _listwatchesFilm.send(listWatchesFilm)
        }
    }

    fun getSearchListPagging(keyWord: String){
        viewModelScope.launch {
            val filter = filterLocalUseCase.getFilterLocal()

            val type = when (filter.type) {
                TypeFilmFilter.ALL -> TYPE[2]
                TypeFilmFilter.FILM -> TYPE[0]
                TypeFilmFilter.TV_SERIES -> TYPE[1]
            }
            val sort = when (filter.sort) {
                SortFilter.RATING -> SORT[0]
                SortFilter.NUM_VOTE -> SORT[1]
                SortFilter.YEAR -> SORT[2]
            }

            //find id country and genre
            val idCountry = getFilterCountryAndGenre.getFilterCountry().first { it.country == filter.country }.id
            val idGenre = getFilterCountryAndGenre.getFilterGenre().first { it.genre == filter.genre.lowercase() }.id

            val filterForSearch = FilterSearch(
                type,
                idCountry,
                idGenre,
                filter.yearFrom.toInt(),
                filter.yearTo.toInt(),
                filter.ratingFrom,
                filter.ratingTo,
                sort,
                filter.watch,
                keyWord)

            //при первом заходе на фрагмент почему-то не показывает список
            listSearchPaggin = Pager(
                config = PagingConfig(pageSize = 20),
                initialKey = null,
                pagingSourceFactory = { SearchPaggingSource(filterForSearch,getSearchFilms) }
            ).flow.cachedIn(viewModelScope)
            Log.d("startRC","creat $listSearchPaggin")
        }
    }



    companion object {
        private val TYPE = arrayListOf("FILM", "TV_SERIES", "ALL")
        private val SORT = arrayListOf("RATING", "NUM_VOTE", "YEAR")
    }
}