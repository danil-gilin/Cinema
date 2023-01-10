package com.example.cinema.presenter.home.homepage.allFilm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.entity.typeListFilm.TypeListFilm
import com.example.cinema.service.cinemaPaggingAdapter.CinemaPaggingSource
import com.example.cinema.service.cinemaPaggingAdapter.CinemaTopPagginSource
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class AllFilmViewModel @Inject constructor() : ViewModel() {
    var pagedCinema: Flow<PagingData<Cinema>>?=null
    var typeListTop: Flow<PagingData<Film>>?=null

    fun getCinema(typeListFilm: TypeListFilm) {
        try {
            Log.d("ClickAllFilm", "getCinema: ${typeListFilm.name}")
            if (typeListFilm.topFilmType != null) {
                typeListTop = Pager(
                    config = PagingConfig(pageSize = 20),
                    initialKey = null,
                    pagingSourceFactory = { CinemaTopPagginSource(typeListFilm) }
                ).flow.cachedIn(viewModelScope)
            } else {
                pagedCinema = Pager(
                    config = PagingConfig(pageSize = 20),
                    initialKey = null,
                    pagingSourceFactory = { CinemaPaggingSource(typeListFilm) }
                ).flow.cachedIn(viewModelScope)
            }
        }catch (e : Exception){
            Log.d("ClickAllFilm", "getCinema: ${e.message}")
        }
    }
}