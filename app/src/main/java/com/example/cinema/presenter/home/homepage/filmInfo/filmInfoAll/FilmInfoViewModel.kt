package com.example.cinema.presenter.home.homepage.filmInfo.filmInfoAll

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilmFullInfo
import com.example.cinema.domain.WatchFilmUseCase
import com.example.cinema.entity.dbCinema.WatchFilm
import com.example.cinema.entity.filmInfo.FilmInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmInfoViewModel @Inject constructor(
    private val getFilmFullInfo: GetFilmFullInfo,
    private val watchFilmUseCase: WatchFilmUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow<FilmInfoState>(FilmInfoState.Loading)
    val state = _state.asStateFlow()

    private val _watchsFilm = Channel<List<Int>> { }
    val watchsFilm = _watchsFilm.receiveAsFlow()

    var localFilm: FilmInfo? = null


    fun getFilm(id: Int) {
        try {
            viewModelScope.launch {
                _state.value = FilmInfoState.Loading
                var short_info_1 = ""
                var short_info_2 = ""
                var short_info_3 = ""
                val film = getFilmFullInfo.getFilmInfo(id)
                localFilm = film
                //short 1
                if (film?.ratingKinopoisk != null) {
                    short_info_1 += film.ratingKinopoisk
                } else if (film?.ratingImdb != null) {
                    short_info_1 += film.ratingImdb
                } else if (film?.ratingFilmCritics != null) {
                    short_info_1 += film.ratingFilmCritics
                }
                if (film?.nameRu != null) {
                    short_info_1 += " ${film.nameRu}"
                } else if (film?.nameEn != null) {
                    short_info_1 += " ${film.nameEn}"
                } else if (film?.nameOriginal != null) {
                    short_info_1 += " ${film.nameOriginal}"
                }

                //short 2
                short_info_2 += "${film?.year},"
                var infoSerial: String? = null
                if (film?.serial == true) {
                    val infoSerialItem = getFilmFullInfo.getSerialInfo(id)
                    infoSerialItem?.items?.size.let {
                        if (it != null && it.toString() in TenNumber || it.toString()
                                .last() == '0'
                        ) {
                            Log.d("SessonName", "${it} ${it.toString() in "10".."20"} 1")
                            short_info_2 += " ${it} ??????????????,"
                            infoSerial = "${it} ??????????????,"
                        } else if (it.toString().last() == '1') {
                            Log.d("SessonName", "2")
                            short_info_2 += " ${it} ??????????,"
                            infoSerial = "${it} ??????????,"
                        } else if (it.toString().last() in '2'..'4') {
                            Log.d("SessonName", "3")
                            short_info_2 += " ${it} ????????????,"
                            infoSerial = "${it} ????????????,"
                        } else if (it.toString().last() in '5'..'9') {
                            Log.d("SessonName", "4")
                            short_info_2 += " ${it} ??????????????,"
                            infoSerial = "${it} ??????????????,"
                        }
                    }
                    var sumEpisod = 0
                    infoSerialItem?.items?.forEach {
                        sumEpisod += it.episodes.size
                    }
                    if (sumEpisod != null && sumEpisod.toString() in TenNumber || sumEpisod.toString()
                            .last() == '0'
                    ) {
                        infoSerial += " ${sumEpisod} ??????????"
                    } else if (sumEpisod.toString().last() == '1') {
                        infoSerial += " ${sumEpisod} ??????????"
                    } else if (sumEpisod.toString().last() in '2'..'4') {
                        infoSerial += " ${sumEpisod} ??????????"
                    } else if (sumEpisod.toString().last() in '5'..'9') {
                        infoSerial += " ${sumEpisod} ??????????"
                    }
                }
                if (film?.genres != null) {
                    film.genres.forEachIndexed { index, genre ->
                        if (index == 2) {
                            short_info_2 += " ${genre.genre}"
                        } else if (index < 1) {
                            if (film.genres.size == 1) {
                                short_info_2 += " ${genre.genre}"
                            } else {
                                short_info_2 += " ${genre.genre},"
                            }
                        }
                    }
                }


                //short 3
                if (film?.countries != null) {
                    short_info_3 += " ${film.countries[0].country},"
                }
                if (film?.filmLength != null) {
                    val hours = film.filmLength / 60
                    val minutes = film.filmLength % 60
                    if (film.ratingAgeLimits == null) {
                        if (hours == 0) {
                            short_info_3 += " ${minutes} ??????????"
                        } else {
                            short_info_3 += " $hours ?? $minutes ??????"
                        }
                    } else {
                        if (hours == 0) {
                            short_info_3 += " ${minutes} ??????????,"
                        } else {
                            short_info_3 += " $hours ?? $minutes ??????,"
                        }
                    }
                }
                if (film?.ratingAgeLimits != null) {
                    short_info_3 += " ${film.ratingAgeLimits.filter { it.isDigit() }}+"
                }
                val listActorAndWorker = getFilmFullInfo.getActorAndWorker(id)
                val listGallery = getFilmFullInfo.getGalerryFilm(id)
                val listSimilar = getFilmFullInfo.getSimilarFilms(id)

                val filmDescription: String = film?.description ?: ""
                val filmShortDescription: String = film?.shortDescription ?: ""

                val filmName = film?.nameRu ?: film?.nameEn ?: film?.nameOriginal ?: ""

                val isWatchFilm= watchFilmUseCase.getWatchFilm(film!!.kinopoiskId) !=null
                val filmsWatch=watchFilmUseCase.getWatchFilmId()


                _state.value = FilmInfoState.Success(
                    short_info_1,
                    short_info_2,
                    short_info_3,
                    filmName,
                    film?.posterUrlPreview ?: "",
                    film?.logoUrl,
                    ("?? ???????????? ??????????????????" to (listActorAndWorker?.filter { it.professionKey == "ACTOR" && (it.nameRu != "" || it.nameEn != "") }
                        ?: emptyList())),
                    ("?????? ?????????????? ????????????????" to (listActorAndWorker?.filter { it.professionKey != "ACTOR" && (it.nameRu != "" || it.nameEn != "") }
                        ?: emptyList())),
                    ("??????????????" to (listGallery?.items ?: emptyList())),
                    ("?????????????? ????????????" to (listSimilar?.items ?: emptyList())),
                    film?.genres?.get(0)?.genre ?: "",
                    infoSerial,
                    filmDescription,
                    filmShortDescription,
                    isWatchFilm,
                    filmsWatch
                )
            }
        } catch (e: Exception) {
            _state.value = FilmInfoState.Error(e.message.toString())
        }
    }

    fun addWatchFilm(stateWatch: Boolean) {
        viewModelScope.launch {
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
        }
    }

    fun getWatchesFilm() {
        try {
            viewModelScope.launch {
                _watchsFilm.send(watchFilmUseCase.getWatchFilmId())
            }
        }catch (e:Exception){

        }

    }

    companion object {
        private val TenNumber =
            listOf<String>("10", "11", "12", "13", "14", "15", "16", "17", "18", "19")
    }
}