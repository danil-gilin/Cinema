package com.example.cinema.presenter.home.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetCinemaGenreUseCase
import com.example.cinema.domain.GetGenreNameUseCase
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getCinemaGenreUseCase: GetCinemaGenreUseCase,
    private val getGenreNameUseCase: GetGenreNameUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomePageState>(HomePageState.Loading)
    val state = _state.asStateFlow()

    private val _cinemaGenreChannel1 = Channel<Pair<String, AllCinema>> { }
    val cinemaGenreChannel1 = _cinemaGenreChannel1.receiveAsFlow()

    private val _cinemaGenreChannel2 = Channel<Pair<String, AllCinema>> { }
    val cinemaGenreChannel2 = _cinemaGenreChannel2.receiveAsFlow()

    private val _cinemaPremiers = Channel<Pair<String, AllCinema>> { }
    val cinemaPremiers = _cinemaPremiers.receiveAsFlow()

    private val _cinemaSerial = Channel<Pair<String, AllCinema>> { }
    val cinemaSerial = _cinemaSerial.receiveAsFlow()

    private val _cinemaPopularFilm1 = Channel<Pair<String, CinemaTop>> { }
    val cinemaPopularFilm1 = _cinemaPopularFilm1.receiveAsFlow()

    private val _cinemaPopularFilm2 = Channel<Pair<String, CinemaTop>> { }
    val cinemaPopularFilm2 = _cinemaPopularFilm2.receiveAsFlow()

    private val _test = Channel<String> { }
    val test = _test.receiveAsFlow()

    fun getCinema() {
        viewModelScope.launch {
        try {
            _state.value = HomePageState.Loading
            getCinemaGenre(_cinemaGenreChannel1)
            getCinemaGenre(_cinemaGenreChannel2)
            getCinemaPremiers()
            getSerial()
            getPopularFilm("TOP_250_BEST_FILMS", "ТОП 250", _cinemaPopularFilm1)
            getPopularFilm("TOP_AWAIT_FILMS", "Топ ожидаемых", _cinemaPopularFilm2)
            _state.value = HomePageState.Success
        }catch (e: Exception){
            _state.value = HomePageState.Error("Придумайте название \n для вашей новой коллекции |")
        }
        }
    }

    private suspend fun getCinemaGenre(channel: Channel<Pair<String, AllCinema>>) {
        val genreName = getGenreNameUseCase.getGenreName()
        var genre = 0
        var country = 0
        var allCinema: AllCinema? = null
        var page = 10
        var nameGenre = ""
        try {
            while (true) {
                when ((0..100).random()) {
                    in 0..60 -> {
                        genre = (0 until genreName.genres.size).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenre(genre + 1, 1)
                        page = allCinema.totalPages ?: 1
                        if (page == 0) {
                            page = 1
                        }
                        val tempPage = (1..page).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenre(genre + 1, tempPage)
                        nameGenre = genreName.genres[genre].genre
                    }
                    in 61..80 -> {
                        country = (0 until genreName.countries.size).random()
                        genre = (0 until genreName.genres.size).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenreWithCountry(
                            country + 1,
                            genre + 1,
                            1
                        )
                        nameGenre =
                            genreName.genres[genre].genre + " " + genreName.countries[country].country
                        page = allCinema.totalPages ?: 1
                        if (page == 0) {
                            page = 1
                        }
                        val tempPage = (1..page).random()
                        allCinema = getCinemaGenreUseCase.getCinemaGenreWithCountry(
                            country + 1,
                            genre + 1,
                            tempPage
                        )
                    }
                    in 81..100 -> {
                        country = (0 until genreName.countries.size).random()
                        allCinema = getCinemaGenreUseCase.getCountryCinema(country + 1, 1)
                        page = allCinema.totalPages ?: 1
                        if (page == 0) {
                            page = 1
                        }
                        val tempPage = (1..page).random()
                        allCinema = getCinemaGenreUseCase.getCountryCinema(country + 1, tempPage)
                        nameGenre = genreName.countries[country].country
                    }
                }
                if (allCinema?.items?.isNotEmpty() == true) {
                    if (allCinema.items.size > 20) {
                        channel.send(Pair(nameGenre, allCinema))
                    } else {
                        channel.send(Pair(nameGenre, allCinema))
                    }
                    break
                }
            }
        } catch (e: Exception) {
        }
    }

    private suspend fun getCinemaPremiers() {
        try {
            val month =
                Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
                    ?.uppercase()
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val cinema = getCinemaGenreUseCase.getPremieresFilm(year, month!!)
            _cinemaPremiers.send(Pair("Премьеры", cinema))
        } catch (e: Exception) {
        }
    }

    private suspend fun getPopularFilm(
        type: String,
        genreName: String,
        channel: Channel<Pair<String, CinemaTop>>
    ) {
        try {
            var page = 10
            while (true) {
                val cinema = getCinemaGenreUseCase.getPopularFilm(type, (1..page).random())
                if (cinema.films.isEmpty()) {
                    page = cinema.pagesCount
                    if (page == 0) {
                        page = 1
                    }
                } else {
                    channel.send(Pair(genreName, cinema))
                    break
                }
            }
        } catch (e: Exception) {
        }
    }

    private suspend fun getSerial() {
        try {
            var page = 10
            while (true) {
                val cinema = getCinemaGenreUseCase.getSerial((1..page).random())
                if (cinema.items.isEmpty()) {
                    page = cinema.totalPages ?: 1
                    if (page == 0) {
                        page = 1
                    }
                } else {
                    _cinemaSerial.send(Pair("Сериалы", cinema))
                    break
                }
            }
        } catch (e: Exception) {
        }
    }
}