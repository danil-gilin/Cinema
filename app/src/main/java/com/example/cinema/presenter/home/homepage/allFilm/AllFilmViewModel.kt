package com.example.cinema.presenter.home.homepage.allFilm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.domain.GetActorWorkerInfo

import com.example.cinema.domain.GetFilmFullInfo
import com.example.cinema.domain.WatchFilmUseCase
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.entity.fullInfoActor.FilmWithPosterAndActor
import com.example.cinema.entity.similarFilm.SimilarFilm
import com.example.cinema.entity.similarFilm.SimilarItem
import com.example.cinema.entity.fullInfoActor.typeListFilm.TypeListFilm
import com.example.cinema.service.cinemaPaggingAdapter.CinemaPaggingSource
import com.example.cinema.service.cinemaPaggingAdapter.CinemaTopPagginSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

class AllFilmViewModel @Inject constructor(
    private val getFilmFullInfo: GetFilmFullInfo,
    private val getActorWorkerInfo: GetActorWorkerInfo,
    private val watchFilmUseCase: WatchFilmUseCase
) : ViewModel() {
    var pagedCinema: Flow<PagingData<Cinema>>? = null
    var typeListTop: Flow<PagingData<Film>>? = null
    private val _semilarFilm = Channel<Pair<String, List<SimilarItem>>> { }
    val semilarFilm = _semilarFilm.receiveAsFlow()

    private val _actorFilm = Channel<List<FilmWithPosterAndActor>> { }
    val actorFilm = _actorFilm.receiveAsFlow()

    private val _state = MutableStateFlow<AllFilmState>(AllFilmState.Loading)
    val state = _state.asStateFlow()


    fun getCinema(typeListFilm: TypeListFilm) {
        viewModelScope.launch {
            try {
                Log.d("ClickAllFilm", "getCinema: ${typeListFilm.name}")
                if (typeListFilm.topFilmType != null) {
                    Log.d("ClickAllFilm", "getCinema: ${typeListFilm.name} 1")
                    typeListTop = Pager(
                        config = PagingConfig(pageSize = 20),
                        initialKey = null,
                        pagingSourceFactory = { CinemaTopPagginSource(typeListFilm) }
                    ).flow.cachedIn(viewModelScope)
                } else {
                    if (typeListFilm.semilarFilmId != null) {
                        val semiralFilmList =
                            getFilmFullInfo.getSimilarFilms(typeListFilm.semilarFilmId!!)
                        val genre =
                            getFilmFullInfo.getFilmInfo(typeListFilm.semilarFilmId!!)?.genres?.first()!!.genre
                        _semilarFilm.send((genre to semiralFilmList!!.items))
                    } else {
                        pagedCinema = Pager(
                            config = PagingConfig(pageSize = 20),
                            initialKey = null,
                            pagingSourceFactory = { CinemaPaggingSource(typeListFilm) }
                        ).flow.cachedIn(viewModelScope)
                    }
                }

            } catch (e: Throwable) {
                _state.value = AllFilmState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    fun getCinemaForActor(typeListFilm: TypeListFilm) {
        viewModelScope.launch {
            try {
                val actorFilm = getActorWorkerInfo.getActorWorkerInfo(typeListFilm.actorId!!).films
                val listUrlFilmPreview = arrayListOf<FilmWithPosterAndActor>()
                val exclusive =
                    actorFilm?.sortedByDescending { it.rating }?.distinctBy { it.filmId }
                exclusive?.forEach {
                    try {
                        val infoFilm = getFilmFullInfo.getFilmInfo(it.filmId!!)
                        listUrlFilmPreview.add(
                            FilmWithPosterAndActor(
                                it.description,
                                it.filmId,
                                it.general,
                                it.nameEn,
                                it.nameRu,
                                it.professionKey,
                                it.rating,
                                infoFilm?.posterUrlPreview
                            )
                        )
                    }catch (e: Throwable){
                        _state.value = AllFilmState.Error("Во время обработки запроса \nпроизошла ошибка")
                    }
                }
                _actorFilm.send(listUrlFilmPreview)
            }
            catch (e:Throwable){
                _state.value = AllFilmState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    fun getWatchFilms() {
        viewModelScope.launch {
            _state.value = AllFilmState.Loading
            try {
                val watchFilms = watchFilmUseCase.getWatchFilmId()
                _state.value = AllFilmState.Success(watchFilms)
            } catch (e: Throwable) {
                _state.value = AllFilmState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }
}