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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchFilms: GetSearchFilms,
    private val filterLocalUseCase: FilterLocalUseCase,
    private val getFilterCountryAndGenre: GetFilterCountryAndGenre,
    private val watchFilmUseCase: WatchFilmUseCase
) : ViewModel() {
    private val _listwatchesFilm = Channel<List<Int>> { }
    val listwatchesFilm = _listwatchesFilm.receiveAsFlow()

    private val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    val state = _state.asStateFlow()


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
        ""
    )

    var listSearchPaggin: Flow<PagingData<SearchItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        initialKey = null,
        pagingSourceFactory = { SearchPaggingSource(filterForSearch, getSearchFilms) }
    ).flow.cachedIn(viewModelScope)


    fun getWatchesFilm() {

            viewModelScope.launch {
                try {
                val listWatchesFilm = watchFilmUseCase.getWatchFilmId()
                _listwatchesFilm.send(listWatchesFilm)
                } catch (e: Throwable) {
                    _state.value = SearchState.Error("Во время обработки запроса \nпроизошла ошибка")
                }
            }
    }

    fun getSearchListPagging(keyWord: String) {
            viewModelScope.launch {
                try {
                _state.value = SearchState.Loading
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
                val idCountry = if (filter.country == "") {
                    null
                } else {
                    getFilterCountryAndGenre.getFilterCountry()
                        .first { it.country == filter.country }.id
                }
                val idGenre = if (filter.genre == "") {
                    null
                } else {
                    getFilterCountryAndGenre.getFilterGenre()
                        .first { it.genre == filter.genre.lowercase() }.id
                }

                val filterForSearch = FilterSearch(
                    type,
                    idCountry,
                    idGenre,
                    if (filter.yearFrom != "") filter.yearFrom.toInt() else null,
                    if (filter.yearTo != "") filter.yearTo.toInt() else null,
                    filter.ratingFrom,
                    filter.ratingTo,
                    sort,
                    filter.watch,
                    keyWord
                )


                val listCheckForEmpty = getSearchFilms.getSerachFilmsPagging(filterForSearch, 1)
                if (listCheckForEmpty.isEmpty()) {
                    _state.value = SearchState.Empty
                } else {
                    listSearchPaggin = Pager(
                        config = PagingConfig(pageSize = 20),
                        initialKey = null,
                        pagingSourceFactory = {
                            SearchPaggingSource(
                                filterForSearch,
                                getSearchFilms
                            )
                        }
                    ).flow.cachedIn(viewModelScope)
                    _state.value = SearchState.Success(listSearchPaggin)
                }
                } catch (e: Throwable) {
                    _state.value = SearchState.Error("Во время обработки запроса \nпроизошла ошибка")
                }
            }
    }


    companion object {
        private val TYPE = arrayListOf("FILM", "TV_SERIES", "ALL")
        private val SORT = arrayListOf("RATING", "NUM_VOTE", "YEAR")
    }
}