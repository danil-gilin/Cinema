package com.example.cinema.presenter.home.homepage.filmInfo.filmInfoAll

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.*
import com.example.cinema.entity.dbCinema.*
import com.example.cinema.entity.filmInfo.FilmInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmInfoViewModel @Inject constructor(
    private val getFilmFullInfo: GetFilmFullInfo,
    private val watchFilmUseCase: WatchFilmUseCase,
    private val likeFilmUseCase: LikeFilmUseCase,
    private val wantToWatchFilmUseCase: WantToWatchFilmUseCase,
    private val historyLocalFilmUseCase: HistoryLocalFilmUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow<FilmInfoState>(FilmInfoState.Loading)
    val state = _state.asStateFlow()

    private val _watchsFilm = Channel<List<Int>> { }
    val watchsFilm = _watchsFilm.receiveAsFlow()

    private val _filmBottom = Channel<FilmDBLocal> {}
    val filmBottom = _filmBottom.receiveAsFlow()


    private val _webUrl = Channel<String> { }
    val webURL = _webUrl.receiveAsFlow()

    var localFilm: FilmInfo? = null


    fun getFilm(id: Int) {
        viewModelScope.launch {
            try {
                _state.value = FilmInfoState.Loading
                var short_info_1 = ""
                var short_info_2 = ""
                var short_info_3 = ""
                var film = getFilmFullInfo.getFilmInfo(id)
                localFilm = film

                if (film == null) {
                    _state.value =
                        FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
                    return@launch
                }
                //short 1
                if (film.ratingKinopoisk != null) {
                    short_info_1 += film.ratingKinopoisk
                } else if (film.ratingImdb != null) {
                    short_info_1 += film.ratingImdb
                } else if (film.ratingFilmCritics != null) {
                    short_info_1 += film.ratingFilmCritics
                }
                if (film.nameRu != null) {
                    short_info_1 += " ${film.nameRu}"
                } else if (film.nameEn != null) {
                    short_info_1 += " ${film.nameEn}"
                } else if (film.nameOriginal != null) {
                    short_info_1 += " ${film.nameOriginal}"
                }

                //short 2
                short_info_2 += "${film.year},"
                var infoSerial: String? = null
                if (film.serial == true) {
                    val infoSerialItem = getFilmFullInfo.getSerialInfo(id)
                    infoSerialItem?.items?.size.let {
                        if (it != null && it.toString() in TenNumber || it.toString()
                                .last() == '0'
                        ) {
                            Log.d("SessonName", "${it} ${it.toString() in "10".."20"} 1")
                            short_info_2 += " ${it} сезонов,"
                            infoSerial = "${it} сезонов,"
                        } else if (it.toString().last() == '1') {
                            Log.d("SessonName", "2")
                            short_info_2 += " ${it} сезон,"
                            infoSerial = "${it} сезон,"
                        } else if (it.toString().last() in '2'..'4') {
                            Log.d("SessonName", "3")
                            short_info_2 += " ${it} сезона,"
                            infoSerial = "${it} сезона,"
                        } else if (it.toString().last() in '5'..'9') {
                            Log.d("SessonName", "4")
                            short_info_2 += " ${it} сезонов,"
                            infoSerial = "${it} сезонов,"
                        }
                    }
                    var sumEpisod = 0
                    infoSerialItem?.items?.forEach {
                        sumEpisod += it.episodes.size
                    }
                    if (sumEpisod.toString() in TenNumber || sumEpisod.toString()
                            .last() == '0'
                    ) {
                        infoSerial += " ${sumEpisod} серий"
                    } else if (sumEpisod.toString().last() == '1') {
                        infoSerial += " ${sumEpisod} серия"
                    } else if (sumEpisod.toString().last() in '2'..'4') {
                        infoSerial += " ${sumEpisod} серии"
                    } else if (sumEpisod.toString().last() in '5'..'9') {
                        infoSerial += " ${sumEpisod} серий"
                    }
                }
                if (film.genres != null) {
                    film.genres!!.forEachIndexed { index, genre ->
                        if (index == 2) {
                            short_info_2 += " ${genre.genre}"
                        } else if (index < 1) {
                            if (film.genres!!.size == 1) {
                                short_info_2 += " ${genre.genre}"
                            } else {
                                short_info_2 += " ${genre.genre},"
                            }
                        }
                    }
                }


                //short 3
                if (film.countries != null) {
                    short_info_3 += " ${film.countries!![0].country},"
                }
                if (film.filmLength != null) {
                    val hours = film.filmLength!! / 60
                    val minutes = film.filmLength!! % 60
                    if (film.ratingAgeLimits == null) {
                        if (hours == 0) {
                            short_info_3 += " ${minutes} минут"
                        } else {
                            short_info_3 += " $hours ч $minutes мин"
                        }
                    } else {
                        if (hours == 0) {
                            short_info_3 += " ${minutes} минут,"
                        } else {
                            short_info_3 += " $hours ч $minutes мин,"
                        }
                    }
                }
                if (film.ratingAgeLimits != null) {
                    short_info_3 += " ${film.ratingAgeLimits!!.filter { it.isDigit() }}+"
                }
                val listActorAndWorker = getFilmFullInfo.getActorAndWorker(id)
                val listGallery = getFilmFullInfo.getGalerryFilm(id)
                val listSimilar = getFilmFullInfo.getSimilarFilms(id)

                val filmDescription: String = film.description ?: ""
                val filmShortDescription: String = film.shortDescription ?: ""

                val filmName = film.nameRu ?: film.nameEn ?: film.nameOriginal ?: ""

                val isWatchFilm = watchFilmUseCase.getWatchFilm(film.kinopoiskId) != null
                val filmsWatch = watchFilmUseCase.getWatchFilmId()
                val isLike = likeFilmUseCase.getLikeFilm(film.kinopoiskId) != null
                val isWantToWatch =
                    wantToWatchFilmUseCase.getWantToWatchFilmFilm(film.kinopoiskId) != null


                historyLocalFilmUseCase.addHistoryLocal(
                    HistoryCollectionDB(
                        0,
                        film.kinopoiskId,
                        filmName,
                        film.genres?.get(0)?.genre ?: "",
                        film.posterUrlPreview,
                        film.ratingKinopoisk ?: 0.0,
                        true,
                        System.currentTimeMillis()
                    )
                )


                _state.value = FilmInfoState.Success(
                    short_info_1,
                    short_info_2,
                    short_info_3,
                    filmName,
                    film.posterUrlPreview ?: "",
                    film.logoUrl,
                    ("В фильме снимались" to (listActorAndWorker?.filter { it.professionKey == "ACTOR" && (it.nameRu != "" || it.nameEn != "") }
                        ?: emptyList())),
                    ("Над фильмом работали" to (listActorAndWorker?.filter { it.professionKey != "ACTOR" && (it.nameRu != "" || it.nameEn != "") }
                        ?: emptyList())),
                    ("Галерея" to (listGallery?.items ?: emptyList())),
                    ("Похожие фильмы" to (listSimilar?.items ?: emptyList())),
                    film.genres?.get(0)?.genre ?: "",
                    infoSerial,
                    filmDescription,
                    filmShortDescription,
                    isWatchFilm,
                    filmsWatch,
                    isLike,
                    isWantToWatch
                )
            } catch (e: Throwable) {
                _state.value = FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    fun addWatchFilm(stateWatch: Boolean) {
        viewModelScope.launch {
            try {
                if (stateWatch) {
                    if (localFilm != null) {
                        val name =
                            localFilm?.nameRu ?: localFilm?.nameEn ?: localFilm?.nameOriginal ?: ""
                        val filmToDb = WatchFilm(
                            localFilm!!.kinopoiskId,
                            name,
                            localFilm!!.posterUrl,
                            localFilm!!.genres?.get(0)?.genre ?: "",
                            localFilm!!.ratingKinopoisk,
                            localFilm?.serial == true
                        )
                        watchFilmUseCase.addWatchFilm(filmToDb)
                    }
                } else {
                    watchFilmUseCase.delWatchFilm(localFilm!!.kinopoiskId)
                }
            } catch (e: Throwable) {
                _state.value = FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    fun getWatchesFilm() {
        viewModelScope.launch {
            try {
                _watchsFilm.send(watchFilmUseCase.getWatchFilmId())
            } catch (e: Throwable) {
                _state.value = FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }

    }

    fun addLikeFilm(stateLike: Boolean) {
        viewModelScope.launch {
            try {
                if (stateLike) {
                    if (localFilm != null) {
                        val name =
                            localFilm?.nameRu ?: localFilm?.nameEn ?: localFilm?.nameOriginal ?: ""
                        val filmToDb = LikeFilm(
                            localFilm!!.kinopoiskId,
                            name,
                            localFilm!!.posterUrl,
                            localFilm!!.genres?.get(0)?.genre ?: "",
                            localFilm!!.ratingKinopoisk,
                            localFilm?.serial == true
                        )
                        likeFilmUseCase.addLikeFilm(filmToDb)
                    }
                } else {
                    likeFilmUseCase.delLikeFilm(localFilm!!.kinopoiskId)
                }
            } catch (e: Throwable) {
                _state.value = FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    fun addWantToWatchFilm(state: Boolean) {
        viewModelScope.launch {
            try {
                if (state) {
                    if (localFilm != null) {
                        val name =
                            localFilm?.nameRu ?: localFilm?.nameEn ?: localFilm?.nameOriginal ?: ""
                        val filmToDb = WantToWatchFilm(
                            localFilm!!.kinopoiskId,
                            name,
                            localFilm!!.posterUrl,
                            localFilm!!.genres?.get(0)?.genre ?: "",
                            localFilm!!.ratingKinopoisk,
                            localFilm?.serial == true
                        )
                        wantToWatchFilmUseCase.addWantToWatchFilmFilm(filmToDb)
                    }
                } else {
                    wantToWatchFilmUseCase.delWantToWatchFilmFilm(localFilm!!.kinopoiskId)
                }
            } catch (e: Throwable) {
                _state.value = FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    fun getUrlWeb() {
        viewModelScope.launch {
            try {
                if (localFilm?.webUrl != null) {
                    _webUrl.send(localFilm!!.webUrl)
                } else {
                    _webUrl.send("Нет ссылки")
                }

            } catch (e: Throwable) {
                _state.value = FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    fun getFilmToBottomSheet() {
        viewModelScope.launch {
            try {
                val name =
                    localFilm?.nameRu ?: localFilm?.nameEn ?: localFilm?.nameOriginal ?: ""
                val film = FilmDBLocal(
                    localFilm!!.kinopoiskId,
                    name,
                    localFilm!!.posterUrlPreview,
                    localFilm!!.genres?.get(0)?.genre ?: "",
                    localFilm!!.ratingKinopoisk,
                    localFilm?.serial == true,
                    watchFilmUseCase.getWatchFilm(localFilm!!.kinopoiskId) != null,
                    localFilm!!.year.toString()
                )
                _filmBottom.send(film)
            } catch (e: Throwable) {
                _state.value = FilmInfoState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    companion object {
        private val TenNumber =
            listOf<String>("10", "11", "12", "13", "14", "15", "16", "17", "18", "19")
    }
}