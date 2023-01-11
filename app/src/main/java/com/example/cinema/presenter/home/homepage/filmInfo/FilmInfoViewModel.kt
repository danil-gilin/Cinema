package com.example.cinema.presenter.home.homepage.filmInfo

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
                if (film.serial) {
                    getFilmFullInfo.getSerialInfo(id).items?.size.let {
                        if (it != null && it == 1) {
                            short_info_2 += " ${it} сезон,"
                        } else {
                            short_info_2 += " ${it} сезона,"
                        }
                    }
                }
                if (film.genres != null) {
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
                if (film.countries != null) {
                    short_info_3 += " ${film.countries[0].country},"
                }
                if (film.filmLength != null) {
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
                if (film.ratingAgeLimits != null) {
                    short_info_3 += " ${film.ratingAgeLimits.filter { it.isDigit() }}+"
                }
                val listActorAndWorker = getFilmFullInfo.getActorAndWorker(id)
                val listGallery = getFilmFullInfo.getGalerryFilm(id)
                val listSimilar = getFilmFullInfo.getSimilarFilms(id)
                var x=listActorAndWorker.filter{ it.professionKey != "ACTOR"&&(it.nameRu!=""||it.nameEn!="" )}.toString()
                Log.d("ListWorker",x)

                _state.value = FilmInfoState.Success(
                    short_info_1,
                    short_info_2,
                    short_info_3,
                    film.posterUrlPreview,
                    film.logoUrl,
                    ("В фильме снимались" to listActorAndWorker.filter { it.professionKey == "ACTOR"&&(it.nameRu!=""||it.nameEn!="") }),
                    ("Над фильмом работали" to listActorAndWorker.filter { it.professionKey != "ACTOR"&&(it.nameRu!=""||it.nameEn!="" )}),
                    ("Галерея" to listGallery.items),
                    ("Похожие фильмы" to listSimilar.items),
                    film.genres?.get(0)?.genre ?: "",
                )

            }
        } catch (e: Exception) {
            _state.value = FilmInfoState.Error(e.message.toString())
        }
    }
}