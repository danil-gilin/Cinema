package com.example.cinema.presenter.home.searchPage.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.FilterLocalUseCase
import com.example.cinema.domain.GetFilterCountryAndGenre
import com.example.cinema.domain.GetSearchFilms
import com.example.cinema.entity.filterEntity.CountryFilter
import com.example.cinema.entity.filterEntity.FilterSearch
import com.example.cinema.entity.filterEntity.SortFilter
import com.example.cinema.entity.filterEntity.TypeFilmFilter
import com.example.cinema.entity.searchFilm.SearchItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchFilms: GetSearchFilms,
    private val filterLocalUseCase: FilterLocalUseCase,
    private val getFilterCountryAndGenre: GetFilterCountryAndGenre
) : ViewModel() {
    private val _listSearchChannel = Channel<List<SearchItem>> { }
    val listSearchChannel = _listSearchChannel.receiveAsFlow()


    fun getSearchList(keyWord: String) {
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

            val listSearch = getSearchFilms.getSerachFilms(filterForSearch)
            _listSearchChannel.send(listSearch)
        }
    }


    companion object {
        private val TYPE = arrayListOf("FILM", "TV_SERIES", "ALL")
        private val SORT = arrayListOf("RATING", "NUM_VOTE", "YEAR")
    }
}