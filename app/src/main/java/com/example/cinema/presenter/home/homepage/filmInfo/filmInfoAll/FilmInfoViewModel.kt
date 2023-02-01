package com.example.cinema.presenter.home.homepage.filmInfo.filmInfoAll

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilmFullInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmInfoViewModel @Inject constructor(private val getFilmFullInfo: GetFilmFullInfo) :
    ViewModel() {
    private val _state = MutableStateFlow<FilmInfoState>(FilmInfoState.Loading)
    val state = _state.asStateFlow()


    fun getFilm(id: Int) {
        try {
            viewModelScope.launch {
                _state.value = FilmInfoState.Loading
                var short_info_1 = ""
                var short_info_2 = ""
                var short_info_3 = ""
                val film = getFilmFullInfo.getFilmInfo(id)
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
                        if (it != null && it.toString() in TenNumber) {
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
                    if (sumEpisod != null && sumEpisod.toString() in TenNumber) {
                        infoSerial += " ${sumEpisod} серий"
                    } else if (sumEpisod.toString().last() == '1') {
                        infoSerial += " ${sumEpisod} серия"
                    } else if (sumEpisod.toString().last() in '2'..'4') {
                        infoSerial += " ${sumEpisod} серии"
                    } else if (sumEpisod.toString().last() in '5'..'0') {
                        infoSerial += " ${sumEpisod} серий"
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
                if (film?.ratingAgeLimits != null) {
                    short_info_3 += " ${film.ratingAgeLimits.filter { it.isDigit() }}+"
                }
                val listActorAndWorker = getFilmFullInfo.getActorAndWorker(id)
                val listGallery = getFilmFullInfo.getGalerryFilm(id)
                val listSimilar = getFilmFullInfo.getSimilarFilms(id)
                var x =
                    listActorAndWorker?.filter { it.professionKey != "ACTOR" && (it.nameRu != "" || it.nameEn != "") }
                        .toString()
                Log.d("ListWorker", x)

                var filmDescription:String= film?.description ?: ""
                var filmShortDescription:String= film?.shortDescription ?: ""

                _state.value = FilmInfoState.Success(
                    short_info_1,
                    short_info_2,
                    short_info_3,
                    film?.posterUrlPreview ?: "",
                    film?.logoUrl,
                    ("В фильме снимались" to (listActorAndWorker?.filter { it.professionKey == "ACTOR" && (it.nameRu != "" || it.nameEn != "") }?:emptyList())),
                    ("Над фильмом работали" to( listActorAndWorker?.filter { it.professionKey != "ACTOR" && (it.nameRu != "" || it.nameEn != "") } ?:emptyList())),
                    ("Галерея" to (listGallery?.items ?:emptyList()) ),
                    ("Похожие фильмы" to (listSimilar?.items ?:emptyList())),
                    film?.genres?.get(0)?.genre ?: "",
                    infoSerial,
                    filmDescription,
                    filmShortDescription
                )
            }
        } catch (e: Exception) {
            _state.value = FilmInfoState.Error(e.message.toString())
        }
    }

    companion object {
        private val TenNumber = listOf<String>("10", "11", "12", "13", "14", "15", "16", "17", "18", "19")
    }
}