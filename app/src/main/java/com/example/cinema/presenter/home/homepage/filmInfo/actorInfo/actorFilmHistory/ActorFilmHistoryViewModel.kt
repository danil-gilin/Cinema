package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFilmHistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetActorWorkerInfo
import com.example.cinema.domain.GetFilmFullInfo
import com.example.cinema.domain.WatchFilmUseCase
import com.example.cinema.entity.fullInfoActor.FilmWithPosterAndActor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActorFilmHistoryViewModel @Inject constructor(
    private val getActorWorkerInfo: GetActorWorkerInfo,
    private val getFilmFullInfo: GetFilmFullInfo,
    private val watchFilmUseCase: WatchFilmUseCase
) : ViewModel() {

    val filterEnabled = MutableStateFlow("ACTOR")

    private val _stateActorFilmHistory = MutableStateFlow<ActorFilmHistoryState>(ActorFilmHistoryState.Loading)
    val stateActorFilmHistory = _stateActorFilmHistory.asStateFlow()

    private val _watchsFilm = Channel<List<Int>> { }
    val watchsFilm = _watchsFilm.receiveAsFlow()

    private val _films = MutableStateFlow<List<FilmWithPosterAndActor>>(emptyList())
    val films: StateFlow<List<FilmWithPosterAndActor>> =
        combine(_films, filterEnabled) { films, filterEnabled ->
            Log.d("ActorFilmHistoryViewModelList", "filterEnabled: $filterEnabled")
            when (filterEnabled) {
                proffesionalKey[0] -> films.filter { it.professionKey == proffesionalKey[0] }
                proffesionalKey[1] -> films.filter { it.professionKey == proffesionalKey[1] }
                proffesionalKey[2] -> films.filter { it.professionKey == proffesionalKey[2] }
                proffesionalKey[3] -> films.filter { it.professionKey == proffesionalKey[3] }
                proffesionalKey[4] -> films.filter { it.professionKey == proffesionalKey[4] }
                proffesionalKey[5] -> films.filter { it.professionKey == proffesionalKey[5] }
                proffesionalKey[6] -> films.filter { it.professionKey == proffesionalKey[6] }
                proffesionalKey[7] -> films.filter { it.professionKey == proffesionalKey[7] }
                proffesionalKey[8] -> films.filter { it.professionKey == proffesionalKey[8] }
                proffesionalKey[9] -> films.filter { it.professionKey == proffesionalKey[9] }
                proffesionalKey[10] -> films.filter { it.professionKey == proffesionalKey[10] }
                proffesionalKey[11] -> films.filter { it.professionKey == proffesionalKey[11] }
                proffesionalKey[12] -> films.filter { it.professionKey == proffesionalKey[12] }
                proffesionalKey[13] -> films.filter { it.professionKey == proffesionalKey[13] }
                proffesionalKey[14] -> films.filter { it.professionKey == proffesionalKey[14] }
                proffesionalKey[15] -> films.filter { it.professionKey == proffesionalKey[15] }
                else -> emptyList()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _films.value
        )


    fun getActorFilmHistory(idActor: Int) {
        viewModelScope.launch {
                val info = getActorWorkerInfo.getActorWorkerInfo(idActor)
                var nameActorWorker = ""
                var listUrlFilmPreview = arrayListOf<FilmWithPosterAndActor>()
                var filterString = arrayListOf<String>()
                val reatingFilm = info.films?.sortedByDescending { it.rating }?.distinctBy { it.filmId }

                reatingFilm?.forEach {
                    val infoFilm = getFilmFullInfo.getFilmInfo(it.filmId!!)
                    val genre = if(infoFilm?.genres?.size!! >0) infoFilm.genres[0].genre else ""
                    listUrlFilmPreview.add(
                        FilmWithPosterAndActor(
                            it.description,
                            it.filmId,
                            it.general,
                            it.nameEn,
                            it.nameRu,
                            it.professionKey,
                            it.rating,
                            infoFilm?.posterUrlPreview,
                            infoFilm?.year.toString() ?: null,
                            genre
                        )
                    )
                    if (!filterString.contains(it.professionKey) && it.professionKey != null) {
                        Log.d("ActorFilmHistoryFragment", "getActorFilmHistory: ${it.professionKey}")
                        filterString.add(it.professionKey)
                    }
                }
                var filterStringWithSize = arrayListOf<Pair<String, Int>>()
                filterString.forEach { filter ->
                    filterStringWithSize.add(
                        Pair(
                            filter,
                            reatingFilm?.filter { it.professionKey == filter }?.size!!
                        )
                    )
                }
                _films.value = listUrlFilmPreview
                if (info.nameRu != null) nameActorWorker = info.nameRu
                else if (info.nameEn != null) nameActorWorker = info.nameEn
                val famel = info.sex != "MALE"

                val watchesFilms=watchFilmUseCase.getWatchFilmId()

                _stateActorFilmHistory.value = ActorFilmHistoryState.Success(
                    filterStringWithSize.sortedByDescending { it.second },
                    nameActorWorker,
                    famel,
                    watchesFilms,
                )
        }
    }

    fun getWatchesFilm() {
        viewModelScope.launch {
            _watchsFilm.send(watchFilmUseCase.getWatchFilmId())
        }
    }

    companion object {
        var proffesionalKey = listOf<String>(
            "WRITER",
            "OPERATOR",
            "EDITOR",
            "COMPOSER",
            "PRODUCER_USSR",
            "HIMSELF",
            "HERSELF",
            "HRONO_TITR_MALE",
            "HRONO_TITR_FEMALE",
            "TRANSLATOR",
            "DIRECTOR",
            "DESIGN",
            "PRODUCER",
            "ACTOR",
            "VOICE_DIRECTOR",
            "UNKNOWN"
        )
    }
}