package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFulInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetActorWorkerInfo
import com.example.cinema.domain.GetFilmFullInfo
import com.example.cinema.domain.HistoryLocalFilmUseCase
import com.example.cinema.domain.WatchFilmUseCase
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.fullInfoActor.Film
import com.example.cinema.entity.fullInfoActor.FilmWithPosterAndActor
import com.example.cinema.presenter.home.homepage.allFilm.AllFilmState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActorInfoViewModel @Inject constructor(
    private val getActorWorkerInfo: GetActorWorkerInfo,
    private val getFilmFullInfo: GetFilmFullInfo,
    private val watchFilmUseCase: WatchFilmUseCase,
    private val historyLocalFilmUseCase: HistoryLocalFilmUseCase
) : ViewModel() {
    private val _stateActroInfo = MutableStateFlow<ActorInfoState>(ActorInfoState.Loading)
    val stateActorInfo = _stateActroInfo.asStateFlow()

    private val _watchsFilm = Channel<List<Int>> { }
    val watchsFilm = _watchsFilm.receiveAsFlow()


    fun getActorInfo(idActor: Int) {
        viewModelScope.launch {
            try {
                _stateActroInfo.value = ActorInfoState.Loading
                var info = getActorWorkerInfo.getActorWorkerInfo(idActor)
                var listUrlFilmPreview = arrayListOf<FilmWithPosterAndActor>()
                var reatingFilm: List<Film>? = null
                if (info.films?.size!! <= 10) {
                    reatingFilm =
                        info.films?.sortedByDescending { it.rating }?.distinctBy { it.filmId }
                } else {
                    reatingFilm =
                        info.films?.sortedByDescending { it.rating }?.distinctBy { it.filmId }
                            ?.subList(0, 11)
                }
                reatingFilm?.forEach {
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
                        _stateActroInfo.value = ActorInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
                    }
                }

                var infoHistoryFilms = ""
                info.films?.distinctBy { it.filmId }?.size.let {
                    Log.d("HistoryFilms", "${it}")
                    if (it != null && it.toString() in TenNumber || it.toString().last() == '0') {
                        infoHistoryFilms = "${it} фильмов"
                    } else if (it.toString().last() == '1') {
                        infoHistoryFilms = "${it} фильм"
                    } else if (it.toString().last() in '2'..'4') {
                        infoHistoryFilms = "${it} фильма"
                    } else if (it.toString().last() in '5'..'9') {
                        infoHistoryFilms = "${it} фильмов"
                    }
                }

                val watchesFilms = watchFilmUseCase.getWatchFilmId()

                val nameActor=info.nameRu ?: info.nameEn ?: ""

                historyLocalFilmUseCase.addHistoryLocal(
                    HistoryCollectionDB(
                        0,
                        info.personId,
                        nameActor,
                        info.profession ?: "",
                        info.posterUrl ?: "",
                         0.0,
                        false,
                       System.currentTimeMillis()
                    )
                )
                Log.d("ActorInfoState", "view model good")
                _stateActroInfo.value =
                    ActorInfoState.Success(info, listUrlFilmPreview, infoHistoryFilms, watchesFilms)
            } catch (e: Throwable) {
                _stateActroInfo.value = ActorInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }


    fun getWatchesFilm() {
        try {
        viewModelScope.launch {
            _watchsFilm.send(watchFilmUseCase.getWatchFilmId())
        }
        }catch (e:Throwable){
            Log.d("ActorInfoState", "view model error")
        }
    }

    companion object {
        private val TenNumber =
            listOf<String>("10", "11", "12", "13", "14", "15", "16", "17", "18", "19")
    }
}