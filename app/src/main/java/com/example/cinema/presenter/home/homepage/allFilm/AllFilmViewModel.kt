package com.example.cinema.presenter.home.homepage.allFilm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.domain.GetFilmFullInfo
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.entity.similarFilm.SimilarFilm
import com.example.cinema.entity.similarFilm.SimilarItem
import com.example.cinema.entity.typeListFilm.TypeListFilm
import com.example.cinema.service.cinemaPaggingAdapter.CinemaPaggingSource
import com.example.cinema.service.cinemaPaggingAdapter.CinemaTopPagginSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

class AllFilmViewModel @Inject constructor(private val getFilmFullInfo: GetFilmFullInfo) : ViewModel() {
    var pagedCinema: Flow<PagingData<Cinema>>?=null
    var typeListTop: Flow<PagingData<Film>>?=null
    private val _semilarFilm = Channel<Pair<String,List<SimilarItem>>> { }
    val semilarFilm = _semilarFilm.receiveAsFlow()

    fun getCinema(typeListFilm: TypeListFilm) {
        viewModelScope.launch {
            try {
                Log.d("ClickAllFilm", "getCinema: ${typeListFilm.name}")
                if (typeListFilm.topFilmType != null) {
                    typeListTop = Pager(
                        config = PagingConfig(pageSize = 20),
                        initialKey = null,
                        pagingSourceFactory = { CinemaTopPagginSource(typeListFilm) }
                    ).flow.cachedIn(viewModelScope)
                } else {
                    if (typeListFilm.semilarFilmId != null) {
                      val semiralFilmList=  getFilmFullInfo.getSimilarFilms(typeListFilm.semilarFilmId!!)
                        val genre=getFilmFullInfo.getFilmInfo(typeListFilm.semilarFilmId!!)?.genres?.first()!!.genre
                        _semilarFilm.send((genre to semiralFilmList!!.items))
                    } else {
                        pagedCinema = Pager(
                            config = PagingConfig(pageSize = 20),
                            initialKey = null,
                            pagingSourceFactory = { CinemaPaggingSource(typeListFilm) }
                        ).flow.cachedIn(viewModelScope)
                    }
                }

            } catch (e: Exception) {
                Log.d("ClickAllFilm", "getCinema: ${e.message}")
            }
        }
    }
}